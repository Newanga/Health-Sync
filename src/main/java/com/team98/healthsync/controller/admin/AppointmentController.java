package com.team98.healthsync.controller.admin;

import com.team98.healthsync.enums.AppointmentState;
import com.team98.healthsync.models.Appointment;
import com.team98.healthsync.models.Channel;
import com.team98.healthsync.models.Patient;
import com.team98.healthsync.repository.AppointmentRepository;
import com.team98.healthsync.repository.ChannelRepository;
import com.team98.healthsync.repository.PatientRepository;
import com.team98.healthsync.sms.TwilioMain;
import com.team98.healthsync.sms.TwillioHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@Transactional
public class  AppointmentController {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/admin/appointments")
    @ResponseBody
    public Iterable<Appointment> getAllChannels(){
        return appointmentRepository.findAll();
    }

    @GetMapping("/admin/appointments/book/{id}")
    public ModelAndView createNewAppointment(@PathVariable("id") @Min(value = 0) @NotNull long id){
        Appointment appointment=new Appointment();
        Channel channel=channelRepository.findById(id).orElseThrow();
        String fullName= String.format("Dr. %s %s",channel.getDoctor().getFirstName(),channel.getDoctor().getLastName());
        channel.getDoctor().setFullName(fullName);
        appointment.setChannel(channel);
        appointment.setPatient(new Patient());
        ModelAndView mv=new ModelAndView();
        mv.addObject("appointment",appointment);
        mv.setViewName("thymeleaf/admin/appointment/new_appointment");
        return mv;
    }


    @PostMapping("/admin/appointments/book/")
    @Transactional
    public ModelAndView NewAppointment(@Valid @ModelAttribute("appointment") Appointment appointment, BindingResult result  ){
        if(result.hasErrors()){
            ModelAndView mv=new ModelAndView();
            mv.addObject("appointment",appointment);
            mv.setViewName("thymeleaf/admin/appointment/new_appointment");
            return mv;
        }

        try{
            Channel channel=channelRepository.findById(appointment.getChannel().getId()).orElseThrow();
            Patient patient=patientRepository.findById(Long.parseLong(appointment.getPatientId())).orElseThrow(()->new EntityNotFoundException());

            List<Appointment> appointemnet=appointmentRepository.findAllByChannel_IdAndPatient_Id(channel.getId(),patient.getId());

            if(appointemnet.spliterator().getExactSizeIfKnown()!=0){
                return new ModelAndView("thymeleaf/admin/appointment/appointments", "error","The Patient has a prebooked appointment for the channel.");
            }

            Long appNo=channel.getPatientCount() +1;
            appointment.setAppointmentNo(appNo);
            appointment.setPatient(patient);
            appointmentRepository.save(appointment);
            channel.setPatientCount(channel.getPatientCount()+1);
            channelRepository.save(channel);


            String formattedContactNo = TwillioHelper.FormatSenderNumber(patient.getContactNo());
            String appNumber=appNo.toString();
            String doctorName= "Dr. " + channel.getDoctor().getFirstName() + " " +channel.getDoctor().getLastName();
            String channelDateTime=channel.getDateTime().toString();
            String message= "Doctor: " + doctorName + ". " + "Date/Time: " + channelDateTime + ". " + "AppointmentNo: " + appNumber + ". " + "Your appointment is confirmed.";
            TwilioMain twilioMain=new TwilioMain(formattedContactNo,message);
            Boolean state=twilioMain.SendMessage();

        }catch (NumberFormatException e ){
            return new ModelAndView("thymeleaf/admin/appointment/appointments", "error","Invalid Patient Id");
        }catch (EntityNotFoundException e ) {
            return new ModelAndView("thymeleaf/admin/appointment/appointments", "error", "Patient Not Found");
        }

        return new ModelAndView("redirect:/admin/appointment","success","true");

    }


    @GetMapping("/admin/appointments/channel/{id}")
    public ModelAndView getChannelAppointments(@PathVariable("id") @Min(value = 0) @NotNull long id){
        return new ModelAndView("thymeleaf/admin/appointment/channel_appointments");
    }


    @GetMapping("/admin/appointments/channel/all/{id}")
    @ResponseBody
    public Iterable<Appointment>  getAllChannelAppointments(@PathVariable("id") @Min(value = 0) @NotNull long id){
        Iterable<Appointment> appointments=appointmentRepository.findByChannelId(id);
        return appointments;
    }

    @GetMapping("/admin/appointments/edit/{id}")
    public ModelAndView editAppointmentById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        String fullName= String.format("Dr. %s %s",appointment.getChannel().getDoctor().getFirstName(),appointment.getChannel().getDoctor().getLastName());
        appointment.getChannel().getDoctor().setFullName(fullName);
        return new ModelAndView("thymeleaf/admin/appointment/edit_appointment","appointment",appointment);
    }


    @PostMapping("/admin/appointments/edit")
    @Transactional
    public ModelAndView editAppointment(@ModelAttribute("id")  long id,
                                    @ModelAttribute("state") AppointmentState state){

        Appointment appointment=appointmentRepository.findById(id).orElse(null);
        appointment.setState(state);
        appointment.setPatientId(appointment.getPatient().getId().toString());

        if(appointment==null){
            return new ModelAndView("thymeleaf/admin/appointment/appointments", "error", "Appointment do not exit.");
        }else {

            try {
                appointmentRepository.save(appointment);
            }
            catch (Exception ex){
                return new ModelAndView("redirect:/admin/appointment","error","fail");
            }

            return new ModelAndView("redirect:/admin/appointment","update","true");
        }


    }
}

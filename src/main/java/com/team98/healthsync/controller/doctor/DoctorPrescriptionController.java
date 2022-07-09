package com.team98.healthsync.controller.doctor;

import com.team98.healthsync.models.Appointment;
import com.team98.healthsync.models.Prescription;
import com.team98.healthsync.repository.AppointmentRepository;
import com.team98.healthsync.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
public class DoctorPrescriptionController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;


    @GetMapping("/doctor/prescriptions/{id}")
    @ResponseBody
    public Iterable<Prescription> getAllPrescriptions(@PathVariable("id")  long id){
        Appointment appointment=appointmentRepository.findById(id).orElse(null);
        return prescriptionRepository.findAllByAppointment_Patient_Id(appointment.getPatient().getId());
    }

    @GetMapping("/doctor/prescriptions/new/{id}")
    public ModelAndView newPrescriptions(@PathVariable("id") @NotNull @Min(value = 0) long id){
        ModelAndView mv=new ModelAndView();
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        Prescription prescription=new Prescription();
        prescription.setAppointment(appointment);
        mv.addObject("prescription",prescription);
        mv.setViewName("thymeleaf/doctor/prescription/new_prescription");
        return mv;
    }


    @PostMapping("/doctor/prescriptions")
    public ModelAndView addNewPrescription(@ModelAttribute("appointment.id") Long id,
                                         @ModelAttribute("description") String desc
    ){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        Prescription pres=new Prescription();
        pres.setDescription(desc);
        pres.setAppointment(appointment);

        prescriptionRepository.save(pres);
        var url = "redirect:/doctor/appointments/" + id;
        return new ModelAndView(url);

    }

    @GetMapping("doctor/prescriptions/{appid}/{id}")
    public ModelAndView getPrescription(@PathVariable("id") @NotNull @Min(value = 0) long id){
        ModelAndView mv=new ModelAndView();
        Prescription prescription=prescriptionRepository.findById(id).orElseThrow();
        String fullName= String.format("Dr. %s %s",prescription.getAppointment().getChannel().getDoctor().getFirstName(), prescription.getAppointment().getChannel().getDoctor().getLastName());
        prescription.getAppointment().getChannel().getDoctor().setFullName(fullName);
        mv.addObject("prescription",prescription);
        mv.setViewName("thymeleaf/doctor/prescription/view_prescription");
        return mv;
    }

}

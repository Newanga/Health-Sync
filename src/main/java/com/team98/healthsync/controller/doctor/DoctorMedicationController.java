package com.team98.healthsync.controller.doctor;

import com.team98.healthsync.models.Appointment;
import com.team98.healthsync.models.Medication;
import com.team98.healthsync.repository.AppointmentRepository;
import com.team98.healthsync.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
public class DoctorMedicationController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicationRepository medicationRepository;


    @GetMapping("/doctor/medications/{id}")
    @ResponseBody
    public Iterable<Medication> getAllMedications(@PathVariable("id")  long id){
        Appointment appointment=appointmentRepository.findById(id).orElse(null);
        return medicationRepository.findAllByAppointment_Patient_Id(appointment.getPatient().getId());
    }

    @GetMapping("/doctor/medications/new/{id}")
    public ModelAndView newMedication(@PathVariable("id") @NotNull @Min(value = 0) long id){
        ModelAndView mv=new ModelAndView();
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        Medication medication=new Medication();
        medication.setAppointment(appointment);
        mv.addObject("medication",medication);
        mv.setViewName("thymeleaf/doctor/medication/new_medication");
        return mv;
    }

    @PostMapping("/doctor/medications")
    public ModelAndView addNewMedication(@ModelAttribute("appointment.id") Long id,
                                         @ModelAttribute("description") String desc
                                         ){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        Medication med=new Medication();
        med.setDescription(desc);
        med.setAppointment(appointment);

        medicationRepository.save(med);
        var url = "redirect:/doctor/appointments/" + id;
        return new ModelAndView(url);

    }


    @GetMapping("doctor/medications/{appid}/{id}")
    public ModelAndView getMedication(@PathVariable("id") @NotNull @Min(value = 0) long id){
        ModelAndView mv=new ModelAndView();
        Medication medication=medicationRepository.findById(id).orElseThrow();
        String fullName= String.format("Dr. %s %s",medication.getAppointment().getChannel().getDoctor().getFirstName(), medication.getAppointment().getChannel().getDoctor().getLastName());
        medication.getAppointment().getChannel().getDoctor().setFullName(fullName);
        mv.addObject("medication",medication);
        mv.setViewName("thymeleaf/doctor/medication/view_medication");
        return mv;
    }
}

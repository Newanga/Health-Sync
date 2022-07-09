package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.Medication;
import com.team98.healthsync.repository.AppointmentRepository;
import com.team98.healthsync.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
public class MedicationController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping("/admin/medications")
    @ResponseBody
    private Iterable<Medication> getAllMedications(){
        return medicationRepository.findAll();
    }

    @GetMapping("/admin/medications/{id}")
    public ModelAndView newMedication(@PathVariable("id") @NotNull @Min(value = 0) long id){
        ModelAndView mv=new ModelAndView();
        Medication medication=medicationRepository.findById(id).orElse(null);
        String fullName= String.format("Dr. %s %s",medication.getAppointment().getChannel().getDoctor().getFirstName(), medication.getAppointment().getChannel().getDoctor().getLastName());
        medication.getAppointment().getChannel().getDoctor().setFullName(fullName);
        mv.addObject("medication",medication);
        mv.setViewName("thymeleaf/admin/medication/view_medication");
        return mv;
    }




}

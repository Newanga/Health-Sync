package com.team98.healthsync.controller.doctor;

import com.team98.healthsync.models.Allergy;
import com.team98.healthsync.models.Appointment;
import com.team98.healthsync.repository.AllergyRepository;
import com.team98.healthsync.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Controller
public class DoctorAllergyController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AllergyRepository allergyRepository;

    @GetMapping("/doctor/allergies/{id}")
    @ResponseBody
    public Iterable<Allergy> getAllAllergies(@PathVariable("id")  long id){
        Appointment appointment=appointmentRepository.findById(id).orElse(null);
        return allergyRepository.findAllByAppointment_Patient_Id(appointment.getPatient().getId());
    }

    @GetMapping("/doctor/allergies/new/{id}")
    public ModelAndView newAllergy(@PathVariable("id") @NotNull @Min(value = 0) long id){
        ModelAndView mv=new ModelAndView();
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        Allergy allergy=new Allergy();
        allergy.setAppointment(appointment);
        mv.addObject("allergy",allergy);
        mv.setViewName("thymeleaf/doctor/allergy/new_allergy");
        return mv;
    }

    @PostMapping("/doctor/allergy")
    @Transactional
    public ModelAndView addNewAllergy(@ModelAttribute("appointment.id") Long id,
                                      @ModelAttribute("title") String title,
                                      @ModelAttribute("description") String desc
    ){
        Appointment appointment=appointmentRepository.findById(id).orElseThrow();
        Allergy allergy=new Allergy();
        allergy.setAppointment(appointment);
        allergy.setDescription(desc);
        allergy.setTitle(title);
        allergyRepository.save(allergy);
        var url = "redirect:/doctor/appointments/" + id;
        return new ModelAndView(url);

    }

}

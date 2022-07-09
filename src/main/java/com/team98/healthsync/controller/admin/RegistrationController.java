package com.team98.healthsync.controller.admin;


import com.team98.healthsync.enums.UserRole;
import com.team98.healthsync.models.*;
import com.team98.healthsync.repository.SpecializationRepository;
import com.team98.healthsync.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private IUserService  userService;

    @Autowired
    private SpecializationRepository specializationRepository;

    @PostMapping("/admin/register/doctor")
    public ModelAndView registerDoctor(@Valid @ModelAttribute("doc")  Doctor doc, BindingResult result  ){
        doc.getAccount().setRole(UserRole.ROLE_DOCTOR);
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("specializations_dropdown",specializationRepository.findAll());
            mv.addObject("doc",doc);
            mv.setViewName("thymeleaf/admin/doctor/new_doctor");
            return mv;
        }
        try {
            Specialization spec=specializationRepository.findById(doc.getSpecializationId()).orElseThrow();
            doc.setSpecialization(spec);
            userService.registerNewDoctor(doc);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/doctor/doctors", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/doctor","success","true");

    }

    @PostMapping("/admin/register/patient")
    public ModelAndView registerPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult result  ){
        patient.getAccount().setRole(UserRole.ROLE_PATIENT);
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("patient",patient);
            mv.setViewName("thymeleaf/admin/patient/new_patient");
            return mv;
        }
        try {
            userService.registerNewPatient(patient);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/patient/patients", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/patient","success","true");

    }


    @PostMapping("/admin/register/receptionist")
    public ModelAndView registerReceptionist(@Valid @ModelAttribute("receptionist") Receptionist receptionist, BindingResult result  ){
        receptionist.getAccount().setRole(UserRole.ROLE_RECEPTIONIST);
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("receptionist",receptionist);
            mv.setViewName("thymeleaf/admin/receptionist/new_receptionist");
            return mv;
        }
        try {
            userService.registerNewReceptionist(receptionist);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/receptionist/receptionists", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/receptionist","success","true");

    }


    @PostMapping("/admin/register/labtechnician")
    public ModelAndView registerLabTechnician(@Valid @ModelAttribute("labtechnician") LabTechnician labTechnician, BindingResult result  ){
        labTechnician.getAccount().setRole(UserRole.ROLE_LABTECHNICIAN);
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("labtechnician",labTechnician);
            mv.setViewName("thymeleaf/admin/labtechnician/new_labtechnician");
            return mv;
        }
        try {
            userService.registerNewLabTechnician(labTechnician);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/labtechnician/labtechnicians", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/labtechnician","success","true");

    }

    @PostMapping("/admin/register/pharmacist")
    public ModelAndView registerPharmacist(@Valid @ModelAttribute("pharmacist") Pharmacist pharmacist, BindingResult result  ){
        pharmacist.getAccount().setRole(UserRole.ROLE_PHARMACIST);
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("pharmacist",pharmacist);
            mv.setViewName("thymeleaf/admin/pharmacist/new_pharmacist");
            return mv;
        }
        try {
            userService.registerNewPharmacist(pharmacist);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/pharmacist/pharmacists", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/pharmacist","success","true");

    }

}

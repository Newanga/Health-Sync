package com.team98.healthsync.controller.receptionist;

import com.team98.healthsync.enums.UserRole;
import com.team98.healthsync.models.Patient;
import com.team98.healthsync.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ReceptionistRegisterController {

    @Autowired
    private IUserService userService;

    @PostMapping("/receptionist/register/patient")
    public ModelAndView registerPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult result  ){
        patient.getAccount().setRole(UserRole.ROLE_PATIENT);
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("patient",patient);
            mv.setViewName("thymeleaf/receptionist/patient/new_patient");
            return mv;
        }
        try {
            userService.registerNewPatient(patient);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/receptionist/patient/patients", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/receptionist/patient","success","true");

    }
}

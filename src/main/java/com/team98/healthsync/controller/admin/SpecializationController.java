package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.Specialization;
import com.team98.healthsync.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
public class SpecializationController {

    @Autowired
    private SpecializationRepository specializationRepository;

    @GetMapping("/admin/specializations/new")
    public ModelAndView newSpecialization(){
        return new ModelAndView("thymeleaf/admin/specialization/new_specialization","spec",new Specialization());
    }

    @GetMapping("/admin/specializations")
    @ResponseBody
    public Iterable<Specialization> getAllSpecializations(){
        Iterable<Specialization> specs=specializationRepository.findAll();
        return specs;
    }

    @PostMapping("/admin/specializations")
    public ModelAndView createNewSpecialization(@Valid @ModelAttribute("spec") Specialization spec, BindingResult result  ){
        if(result.hasErrors()) {
            return new ModelAndView("thymeleaf/admin/specialization/new_specialization","spec",spec);
        }
        try {
            specializationRepository.save(spec);
        } catch (Exception e) {
            return new ModelAndView("thymeleaf/admin/specialization/specializations", "error", e.getMessage());
        }
        return new ModelAndView("redirect:/admin/specialization/","success","true");

    }

    @GetMapping("/admin/specializations/{id}")
    public  ModelAndView getSpecializationById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Specialization> spec=specializationRepository.findById(id);
        if(spec.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/specialization/specializations", "error", "Specialization do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/specialization/view_specialization","spec",spec);
    }


    @GetMapping("/admin/specializations/edit/{id}")
    public ModelAndView editDoctorById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Specialization> spec=specializationRepository.findById(id);
        if(spec.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/specialization/specializations", "error", "Specialization do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/specialization/edit_specialization","spec",spec);
    }

    @PostMapping("/admin/specializations/edit")
    @Transactional
    public ModelAndView editDoctor(@Valid @ModelAttribute("spec")  Specialization spec, BindingResult result  ){
        if(result.hasErrors()) {
            return new ModelAndView("thymeleaf/admin/specialization/new_specialization", "spec", spec);
        }

        Specialization specialization=specializationRepository.findById(spec.getId()).orElse(null);

        if(specialization==null){
            return new ModelAndView("thymeleaf/admin/specialization/specializations", "error", "Specialization not exit.");
        }else {
            specialization.setName(spec.getName());
            specialization.setDescription(spec.getDescription());

            try {
                specializationRepository.save(specialization);
            }
            catch (Exception ex){
                return new ModelAndView("redirect:/admin/specialization","error","fail");
            }

            return new ModelAndView("redirect:/admin/specialization","update","true");
        }


    }


}

package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.LabTechnician;
import com.team98.healthsync.repository.LabTechnicianRepository;
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
public class LabTechnicianController {

    @Autowired
    private LabTechnicianRepository labTechnicianRepository;

    @GetMapping("/admin/labtechnicians")
    @ResponseBody
    public Iterable<LabTechnician> getAllLabTechnicians(){
        Iterable<LabTechnician> labTechnicians=labTechnicianRepository.findAll();
        return labTechnicians;
    }

    @GetMapping("/admin/labtechnicians/new")
    public ModelAndView newLabTechnician(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("labtechnician",new LabTechnician());
        mv.setViewName("thymeleaf/admin/labtechnician/new_labtechnician");
        return mv;
    }

    @GetMapping("/admin/labtechnicians/edit/{id}")
    public ModelAndView editLabTechnicianById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<LabTechnician> labTechnician=labTechnicianRepository.findById(id);
        if(labTechnician.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/labtechnician/labtechnicians", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/labtechnician/edit_labtechnician","labtechnician",labTechnician);
    }

    @PostMapping("/admin/labtechnicians/edit")
    @Transactional
    public ModelAndView editLabTechnician(@Valid @ModelAttribute("labtechnician") LabTechnician labTechnician, BindingResult result  ){
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("labtechnician",labTechnician);
            mv.setViewName("thymeleaf/admin/labtechnician/edit_labtechnician");
            return mv;
        }

        LabTechnician oldLabTechnician=labTechnicianRepository.findById(labTechnician.getId()).orElse(null);

        if(oldLabTechnician==null){
            return new ModelAndView("thymeleaf/admin/labtechnician/labtechnicians", "error", "User do not exit.");
        }else {
            oldLabTechnician.getAccount().setEmail(labTechnician.getAccount().getEmail());
            oldLabTechnician.setDateOfBirth(labTechnician.getDateOfBirth());
            oldLabTechnician.setContactNo(labTechnician.getContactNo());
            oldLabTechnician.setFirstName(labTechnician.getFirstName());
            oldLabTechnician.setNic(labTechnician.getNic());
            oldLabTechnician.setLastName(labTechnician.getLastName());
            oldLabTechnician.setGender(labTechnician.getGender());
            oldLabTechnician.setAddress(labTechnician.getAddress());
            try {
                labTechnicianRepository.save(oldLabTechnician);
            }
            catch (Exception ex){
                return new ModelAndView("redirect:/admin/labtechnician","error","fail");
            }

            return new ModelAndView("redirect:/admin/labtechnician","update","true");
        }


    }

    @GetMapping("/admin/labtechnicians/{id}")
    public  ModelAndView getLabTechnicianById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<LabTechnician> labTechnician=labTechnicianRepository.findById(id);
        if(labTechnician.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/labtechnician/labtechnicians", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/labtechnician/view_labtechnician","labtechnician",labTechnician);
    }

}

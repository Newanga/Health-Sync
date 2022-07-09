package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.Receptionist;
import com.team98.healthsync.repository.ReceptionistRepository;
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
public class ReceptionistController {

    @Autowired
    private ReceptionistRepository receptionistRepository;

    @GetMapping("/admin/receptionists")
    @ResponseBody
    public Iterable<Receptionist> getAllReceptionists(){
        Iterable<Receptionist> receptionists=receptionistRepository.findAll();
        return receptionists;
    }

    @GetMapping("/admin/receptionists/new")
    public ModelAndView newReceptionist(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("receptionist",new Receptionist());
        mv.setViewName("thymeleaf/admin/receptionist/new_receptionist");
        return mv;
    }

    @GetMapping("/admin/receptionists/edit/{id}")
    public ModelAndView editReceptionistById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Receptionist> receptionist=receptionistRepository.findById(id);
        if(receptionist.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/receptionist/receptionists", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/receptionist/edit_receptionist","receptionist",receptionist);
    }

    @PostMapping("/admin/receptionists/edit")
    @Transactional
    public ModelAndView editReceptionist(@Valid @ModelAttribute("receptionist") Receptionist receptionist, BindingResult result  ){
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("receptionist",receptionist);
            mv.setViewName("thymeleaf/admin/receptionist/edit_receptionist");
            return mv;
        }

        Receptionist oldReceptionist=receptionistRepository.findById(receptionist.getId()).orElse(null);

        if(oldReceptionist==null){
            return new ModelAndView("thymeleaf/admin/receptionist/receptionists", "error", "User do not exit.");
        }else {
            oldReceptionist.getAccount().setEmail(receptionist.getAccount().getEmail());
            oldReceptionist.setDateOfBirth(receptionist.getDateOfBirth());
            oldReceptionist.setContactNo(receptionist.getContactNo());
            oldReceptionist.setFirstName(receptionist.getFirstName());
            oldReceptionist.setNic(receptionist.getNic());
            oldReceptionist.setLastName(receptionist.getLastName());
            oldReceptionist.setGender(receptionist.getGender());
            oldReceptionist.setAddress(receptionist.getAddress());
            try {
                receptionistRepository.save(oldReceptionist);
            }
            catch (Exception ex){
                return new ModelAndView("redirect:/admin/receptionist","error","fail");
            }

            return new ModelAndView("redirect:/admin/receptionist","update","true");
        }


    }

    @GetMapping("/admin/receptionists/{id}")
    public  ModelAndView getReceptionistById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Receptionist> receptionist=receptionistRepository.findById(id);
        if(receptionist.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/receptionist/receptionists", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/receptionist/view_receptionist","receptionist",receptionist);
    }
}

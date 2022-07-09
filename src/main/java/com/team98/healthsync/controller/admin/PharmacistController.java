package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.Pharmacist;
import com.team98.healthsync.repository.PharmacistRepository;
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
public class PharmacistController {

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @GetMapping("/admin/pharmacists")
    @ResponseBody
    public Iterable<Pharmacist> getAllPharmacistss(){
        Iterable<Pharmacist> pharmacists=pharmacistRepository.findAll();
        return pharmacists;
    }


    @GetMapping("/admin/pharmacists/new")
    public ModelAndView newPharmacist(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("pharmacist",new Pharmacist());
        mv.setViewName("thymeleaf/admin/pharmacist/new_pharmacist");
        return mv;
    }
    @GetMapping("/admin/pharmacists/edit/{id}")
    public ModelAndView editPharmacisttById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Pharmacist> pharmacist=pharmacistRepository.findById(id);
        if(pharmacist.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/pharmacist/pharmacists", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/pharmacist/edit_pharmacist","pharmacist",pharmacist);
    }


    @PostMapping("/admin/pharmacists/edit")
    @Transactional
    public ModelAndView editPharmacist(@Valid @ModelAttribute("pharmacists") Pharmacist pharmacist, BindingResult result  ){
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("pharmacist",pharmacist);
            mv.setViewName("thymeleaf/admin/pharmacist/edit_pharmacist");
            return mv;
        }

        Pharmacist oldPharmacist=pharmacistRepository.findById(pharmacist.getId()).orElse(null);

        if(oldPharmacist==null){
            return new ModelAndView("thymeleaf/admin/pharmacist/pharmacists", "error", "User do not exit.");
        }else {
            oldPharmacist.getAccount().setEmail(pharmacist.getAccount().getEmail());
            oldPharmacist.setDateOfBirth(pharmacist.getDateOfBirth());
            oldPharmacist.setContactNo(pharmacist.getContactNo());
            oldPharmacist.setFirstName(pharmacist.getFirstName());
            oldPharmacist.setNic(pharmacist.getNic());
            oldPharmacist.setLastName(pharmacist.getLastName());
            oldPharmacist.setGender(pharmacist.getGender());
            oldPharmacist.setAddress(pharmacist.getAddress());
            try {
                pharmacistRepository.save(oldPharmacist);
            }
            catch (Exception ex){
                return new ModelAndView("redirect:/admin/pharmacist","error","fail");
            }

            return new ModelAndView("redirect:/admin/pharmacist","update","true");
        }


    }



    @GetMapping("/admin/pharmacists/{id}")
    public  ModelAndView getPharmacistById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Pharmacist> pharmacist=pharmacistRepository.findById(id);
        if(pharmacist.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/pharmacist/pharmacists", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/pharmacist/view_pharmacist","pharmacist",pharmacist);
    }
}

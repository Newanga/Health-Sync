package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.Doctor;
import com.team98.healthsync.models.Specialization;
import com.team98.healthsync.repository.DoctorRepository;
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
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @GetMapping("/admin/doctors/new")
    public ModelAndView newDoctor(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("specializations_dropdown",specializationRepository.findAll());
        mv.addObject("doc",new Doctor());
        mv.setViewName("thymeleaf/admin/doctor/new_doctor");
        return mv;
    }

    @GetMapping("/admin/doctors")
    @ResponseBody
    public Iterable<Doctor> getAllDoctors(){
         Iterable<Doctor> docs=doctorRepository.findAll();
         return docs;
    }

    @GetMapping("/admin/doctors/edit/{id}")
    public ModelAndView editDoctorById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Doctor> doc=doctorRepository.findById(id);
         if(doc.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/doctor/doctors", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/doctor/edit_doctor","doc",doc);
    }

    @PostMapping("/admin/doctors/edit")
    @Transactional
    public ModelAndView editDoctor(@Valid @ModelAttribute("doc")  Doctor doc, BindingResult result  ){
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("doc",doc);
            mv.setViewName("thymeleaf/admin/doctor/edit_doctor");
            return mv;
        }

        Doctor doctor=doctorRepository.findById(doc.getId()).orElse(null);
        Specialization specFromDB=specializationRepository.findById(doc.getSpecialization().getId()).orElseThrow();

            if(doctor==null){
                return new ModelAndView("thymeleaf/admin/doctor/doctors", "error", "User do not exit.");
            }else {
                doctor.getAccount().setEmail(doc.getAccount().getEmail());
                doctor.setDateOfBirth(doc.getDateOfBirth());
                doctor.setQualification(doc.getQualification());
                doctor.setContactNo(doc.getContactNo());
                doctor.setFirstName(doc.getFirstName());
                doctor.setNic(doc.getNic());
                doctor.setLastName(doc.getLastName());
                doctor.setSpecialization(specFromDB);
                doctor.setGender(doc.getGender());
                doctor.setAddress(doc.getAddress());
                try {
                    doctorRepository.save(doctor);
                }
                catch (Exception ex){
                    return new ModelAndView("redirect:/admin/doctor","error","fail");
                }

                return new ModelAndView("redirect:/admin/doctor","update","true");
            }


    }


    @GetMapping("/admin/doctors/{id}")
    public  ModelAndView getDoctorById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Doctor> doc=doctorRepository.findById(id);
        if(doc.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/doctor/doctors", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/doctor/view_doctor","doc",doc);
    }






}

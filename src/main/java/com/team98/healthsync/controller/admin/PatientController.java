package com.team98.healthsync.controller.admin;

import com.team98.healthsync.models.Patient;
import com.team98.healthsync.repository.PatientRepository;
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
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/admin/patients/new")
    public ModelAndView newPatient(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("patient",new Patient());
        mv.setViewName("thymeleaf/admin/patient/new_patient");
        return mv;
    }

    @GetMapping("/admin/patients")
    @ResponseBody
    public Iterable<Patient> getAllPatients(){
        Iterable<Patient> patients=patientRepository.findAll();
        return patients;
    }



    @GetMapping("/admin/patients/{id}")
    @ResponseBody
    public  ModelAndView getPatientById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Patient> patient=patientRepository.findById(id);
        if(patient.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/patient/patients", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/patient/view_patient","patient",patient);
    }

    @GetMapping("/admin/patients/edit/{id}")
    public ModelAndView editPatientById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Patient> patient=patientRepository.findById(id);
        if(patient.isPresent()==false){
            return new ModelAndView("thymeleaf/admin/patient/patients", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/admin/patient/edit_patient","patient",patient);
    }

    @PostMapping("/admin/patients/edit")
    @Transactional
    public ModelAndView editPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult result  ){
        if(result.hasErrors()) {
            ModelAndView mv=new ModelAndView();
            mv.addObject("patient",patient);
            mv.setViewName("thymeleaf/admin/patient/edit_patient");
            return mv;
        }

        Patient oldPatient=patientRepository.findById(patient.getId()).orElse(null);

        if(oldPatient==null){
            return new ModelAndView("thymeleaf/admin/patient/patients", "error", "User do not exit.");
        }else {
            oldPatient.getAccount().setEmail(patient.getAccount().getEmail());
            oldPatient.setDateOfBirth(patient.getDateOfBirth());
            oldPatient.setContactNo(patient.getContactNo());
            oldPatient.setFirstName(patient.getFirstName());
            oldPatient.setNic(patient.getNic());
            oldPatient.setLastName(patient.getLastName());
            oldPatient.setGender(patient.getGender());
            oldPatient.setAddress(patient.getAddress());
            try {
                patientRepository.save(oldPatient);
            }
            catch (Exception ex){
                return new ModelAndView("redirect:/admin/patient","error","fail");
            }

            return new ModelAndView("redirect:/admin/patient","update","true");
        }


    }
}

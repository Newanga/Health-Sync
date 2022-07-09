package com.team98.healthsync.controller.receptionist;

import com.team98.healthsync.models.Patient;
import com.team98.healthsync.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
public class ReceptionistPatientController {


    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/receptionist/patients")
    @ResponseBody
    public Iterable<Patient> getAllPatients(){
        Iterable<Patient> patients=patientRepository.findAll();
        return patients;
    }

    @GetMapping("/receptionist/patients/new")
    public ModelAndView newPatient(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("patient",new Patient());
        mv.setViewName("thymeleaf/receptionist/patient/new_patient");
        return mv;
    }


    @GetMapping("/receptionist/patients/{id}")
    @ResponseBody
    public  ModelAndView getPatientById(@PathVariable("id") @NotNull @Min(value = 0) long id){
        Optional<Patient> patient=patientRepository.findById(id);
        if(patient.isPresent()==false){
            return new ModelAndView("thymeleaf/receptionist/patient/patients", "error", "User do not exit.");
        }
        return new ModelAndView("thymeleaf/receptionist/patient/view_patient","patient",patient);
    }



}

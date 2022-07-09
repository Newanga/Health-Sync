package com.team98.healthsync.controller.patient;

import com.team98.healthsync.models.Allergy;
import com.team98.healthsync.repository.AllergyRepository;
import com.team98.healthsync.service.IPatHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PatientAllergyController {

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private IPatHelper patientHelper;


    @GetMapping("/patient/allergies")
    @ResponseBody
    private Iterable<Allergy> getPatPrescriptions(){
    return allergyRepository.findAllByAppointment_Patient_Id(patientHelper.GetPatientId());
    }

}

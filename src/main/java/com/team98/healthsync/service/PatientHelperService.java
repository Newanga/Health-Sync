package com.team98.healthsync.service;

import com.team98.healthsync.models.Patient;
import com.team98.healthsync.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PatientHelperService implements IPatHelper{


    @Autowired
    private PatientRepository patientRepository;


    @Override
    public Long GetPatientId() {
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Patient patient=patientRepository.findByAccount_Email(username);
        return patient.getId();
    }
}

package com.team98.healthsync.service;

import com.team98.healthsync.models.Doctor;
import com.team98.healthsync.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DoctorHelperService implements IDocHelper {

    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public Long GetDoctorId() {
        Object principal= SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Doctor doc=doctorRepository.findByAccount_Email(username);
        return doc.getId();
    }
}

package com.team98.healthsync.service;

import com.team98.healthsync.models.*;
import com.team98.healthsync.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private ReceptionistRepository receptionistRepository;

    @Autowired
    private LabTechnicianRepository labTechnicianRepository;

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Doctor registerNewDoctor(Doctor doc) throws Exception  {
        if (emailExist(doc.getAccount().getEmail())){
            throw new Exception("A account already exist with that email address: " + doc.getAccount().getEmail());
        }
        else {
                doc.getAccount().setPassword(passwordEncoder.encode(doc.getAccount().getPassword()));
                final Account savedAccount=accountRepository.save(doc.getAccount());
                doc.setAccount(savedAccount);
                return doctorRepository.save(doc);
        }
    }

    @Override
    public Patient registerNewPatient(Patient patient) throws Exception  {
        if (emailExist(patient.getAccount().getEmail())){
            throw new Exception("A account already exist with that email address: " + patient.getAccount().getEmail());
        }
        else {
            patient.getAccount().setPassword(passwordEncoder.encode(patient.getAccount().getPassword()));
            final Account savedAccount=accountRepository.save(patient.getAccount());
            patient.setAccount(savedAccount);
            return patientRepository.save(patient);
        }
    }

    @Override
    public Receptionist registerNewReceptionist(Receptionist receptionist) throws Exception {
        if (emailExist(receptionist.getAccount().getEmail())){
            throw new Exception("A account already exist with that email address: " + receptionist.getAccount().getEmail());
        }
        else {
            receptionist.getAccount().setPassword(passwordEncoder.encode(receptionist.getAccount().getPassword()));
            final Account savedAccount=accountRepository.save(receptionist.getAccount());
            receptionist.setAccount(savedAccount);
            return receptionistRepository.save(receptionist);
        }
    }

    @Override
    public LabTechnician registerNewLabTechnician(LabTechnician labTechnician) throws Exception {
        if (emailExist(labTechnician.getAccount().getEmail())){
            throw new Exception("A account already exist with that email address: " + labTechnician.getAccount().getEmail());
        }
        else {
            labTechnician.getAccount().setPassword(passwordEncoder.encode(labTechnician.getAccount().getPassword()));
            final Account savedAccount=accountRepository.save(labTechnician.getAccount());
            labTechnician.setAccount(savedAccount);
            return labTechnicianRepository.save(labTechnician);
        }
    }

    @Override
    public Pharmacist registerNewPharmacist(Pharmacist pharmacist) throws Exception {
        if (emailExist(pharmacist.getAccount().getEmail())){
            throw new Exception("A account already exist with that email address: " + pharmacist.getAccount().getEmail());
        }
        else {
            pharmacist.getAccount().setPassword(passwordEncoder.encode(pharmacist.getAccount().getPassword()));
            final Account savedAccount=accountRepository.save(pharmacist.getAccount());
            pharmacist.setAccount(savedAccount);
            return pharmacistRepository.save(pharmacist);
        }
    }


    private boolean emailExist(String email) {
        final Account user = accountRepository.findByEmail(email);
        return user != null;
    }
}

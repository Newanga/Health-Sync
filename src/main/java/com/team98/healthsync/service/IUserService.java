package com.team98.healthsync.service;

import com.team98.healthsync.models.*;

public interface IUserService {
    Doctor registerNewDoctor(Doctor doc) throws Exception;

    Patient registerNewPatient(Patient patient) throws Exception;

    Receptionist registerNewReceptionist(Receptionist receptionist) throws Exception;

    LabTechnician registerNewLabTechnician(LabTechnician labTechnician) throws Exception;

    Pharmacist registerNewPharmacist(Pharmacist pharmacist) throws Exception;
}

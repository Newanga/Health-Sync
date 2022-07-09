package com.team98.healthsync.repository;

import com.team98.healthsync.models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient,Long> {

    Patient findByAccount_Email(final String email);
}

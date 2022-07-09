package com.team98.healthsync.repository;

import com.team98.healthsync.models.Medication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<Medication,Long> {

    Iterable<Medication> findAllByAppointment_Patient_Id(long id);
}

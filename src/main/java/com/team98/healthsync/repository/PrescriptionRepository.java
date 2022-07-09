package com.team98.healthsync.repository;

import com.team98.healthsync.models.Prescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends CrudRepository<Prescription,Long> {

    Iterable<Prescription> findAllByAppointment_Patient_Id(long id);


}

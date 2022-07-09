package com.team98.healthsync.repository;

import com.team98.healthsync.models.Allergy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends CrudRepository<Allergy, Long> {

    Iterable<Allergy> findAllByAppointment_Patient_Id(long id);
}

package com.team98.healthsync.repository;

import com.team98.healthsync.models.Doctor;
import com.team98.healthsync.repository.projection.DoctorFullName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor,Long> {

    List<DoctorFullName> findBy();

    Doctor findByAccount_Email(final String email);
}

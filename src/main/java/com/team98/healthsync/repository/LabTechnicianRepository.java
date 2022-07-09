package com.team98.healthsync.repository;

import com.team98.healthsync.models.LabTechnician;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTechnicianRepository extends CrudRepository<LabTechnician,Long> {
}

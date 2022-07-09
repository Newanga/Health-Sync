package com.team98.healthsync.repository;

import com.team98.healthsync.models.LabTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LabTestRepository extends CrudRepository<LabTest, Long> {
}

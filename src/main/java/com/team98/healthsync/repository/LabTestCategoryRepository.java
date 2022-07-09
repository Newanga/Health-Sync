package com.team98.healthsync.repository;

import com.team98.healthsync.models.LabTestCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTestCategoryRepository extends CrudRepository<LabTestCategory,Long> {
}

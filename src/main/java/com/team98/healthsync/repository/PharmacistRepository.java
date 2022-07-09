package com.team98.healthsync.repository;

import com.team98.healthsync.models.Pharmacist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends CrudRepository<Pharmacist,Long> {
}

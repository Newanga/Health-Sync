package com.team98.healthsync.repository;

import com.team98.healthsync.models.Receptionist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionistRepository extends CrudRepository<Receptionist,Long> {
}

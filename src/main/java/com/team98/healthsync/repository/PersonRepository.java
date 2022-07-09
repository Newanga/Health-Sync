package com.team98.healthsync.repository;

import com.team98.healthsync.models.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonRepository extends CrudRepository<Person, Long> {
}

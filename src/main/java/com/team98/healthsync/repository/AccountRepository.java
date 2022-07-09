package com.team98.healthsync.repository;

import com.team98.healthsync.models.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account,Long> {
    Account findByEmail(String email);
}

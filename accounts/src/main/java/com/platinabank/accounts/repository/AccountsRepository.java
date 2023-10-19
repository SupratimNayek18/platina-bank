package com.platinabank.accounts.repository;

import com.platinabank.accounts.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountsRepository extends MongoRepository<Account,Long> {

    Optional<Account> findByCustomerId(int customerId);

    @Transactional
    void deleteByCustomerId(int customerId);

}

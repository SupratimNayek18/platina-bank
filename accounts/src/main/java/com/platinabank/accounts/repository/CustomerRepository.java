package com.platinabank.accounts.repository;

import com.platinabank.accounts.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CustomerRepository extends MongoRepository<Customer,Integer> {

    Optional<Customer> findByMobileNumber(Long mobileNumber);

}

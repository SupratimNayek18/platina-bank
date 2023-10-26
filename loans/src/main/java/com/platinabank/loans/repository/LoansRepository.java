package com.platinabank.loans.repository;

import com.platinabank.loans.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface LoansRepository extends MongoRepository<Loan,Long> {

    List<Loan> findByMobileNumber(Long mobileNumber);

}

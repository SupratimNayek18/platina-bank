package com.platinabank.loans.repository;

import com.platinabank.loans.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoansRepository extends MongoRepository<Loan,Long> {
}

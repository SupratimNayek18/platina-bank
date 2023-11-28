package com.platinabank.loans;

import com.platinabank.loans.model.Loan;
import com.platinabank.loans.repository.LoansRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
public class RepositoryLayerTest {

    @Autowired
    LoansRepository loansRepository;


    @Test
    public void doFindByMobileNumber(){

        Loan loan1 = new Loan();
        loan1.setLoanId(7894561231L);
        loan1.setMobileNumber(7894561231L);
        loan1.setTotalLoan(10000);

        Loan loan2 = new Loan();
        loan2.setLoanId(7894561232L);
        loan2.setMobileNumber(7894561231L);
        loan2.setTotalLoan(20000);

        loansRepository.save(loan1);
        loansRepository.save(loan2);

        List<Loan> responseList = loansRepository.findByMobileNumber(7894561231L);

        assertEquals(responseList.size(),2);

        loansRepository.delete(loan1);
        loansRepository.delete(loan2);

    }


    @Test
    public void doFindByLoanNumber(){
        Loan loan1 = new Loan();
        loan1.setLoanId(7894561231L);
        loan1.setMobileNumber(7894561231L);
        loan1.setTotalLoan(10000);
        loan1.setLoanNumber(7894561231L);

        loansRepository.save(loan1);

        Optional<Loan> optionalLoan = loansRepository.findByLoanNumber(7894561231L);

        assertNotNull(optionalLoan.get());

        loansRepository.delete(loan1);
    }

}

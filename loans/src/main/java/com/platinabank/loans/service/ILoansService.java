package com.platinabank.loans.service;

import com.platinabank.loans.dto.LoanDto;
import com.platinabank.loans.dto.LoanRequestDto;
import com.platinabank.loans.dto.LoanResponseDto;

public interface ILoansService {

    void createLoan(LoanRequestDto loanRequestDto);

    LoanDto getLoanDetails(Long loanNumber);

    LoanResponseDto getTotalLoanInfo(Long mobileNumber);

    boolean updateLoanDetails(Long loanNumber, int amount);

    boolean deleteLoanDetails(Long loanNumber);

}

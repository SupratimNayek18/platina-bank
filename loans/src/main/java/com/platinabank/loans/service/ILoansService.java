package com.platinabank.loans.service;

import com.platinabank.loans.dto.LoanDto;
import com.platinabank.loans.dto.LoanResponseDto;

public interface ILoansService {

    LoanDto createLoan(LoanDto loanDto);

    LoanDto getLoanDetails(Long loanId);

    LoanResponseDto getTotalLoanInfo(Long mobileNumber);

    void updateLoanDetails(Long loanId, int amount);

    void deleteLoanDetails(Long loanId);

}

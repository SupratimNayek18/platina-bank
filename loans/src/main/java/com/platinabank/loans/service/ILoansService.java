package com.platinabank.loans.service;

import com.platinabank.loans.dto.LoanDto;
import com.platinabank.loans.dto.LoanRequestDto;
import com.platinabank.loans.dto.LoanResponseDto;

public interface ILoansService {

    LoanDto createLoan(LoanRequestDto loanRequestDto);

    LoanDto getLoanDetails(Long loanNumber);

    LoanResponseDto getTotalLoanInfo(Long mobileNumber);

    void updateLoanDetails(Long loanNumber, int amount);

    void deleteLoanDetails(Long loanNumber);

}

package com.platinabank.loans.util.mapper;

import com.platinabank.loans.dto.LoanDto;
import com.platinabank.loans.dto.LoanRequestDto;
import com.platinabank.loans.model.Loan;

public class LoanMapper {

    private LoanMapper(){}

    public static Loan mapToLoan(LoanDto loanDto,Loan loan){

        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setLoanType(loanDto.getLoanType());
        loan.setTotalLoan(loanDto.getTotalLoan());
        loan.setLoanType(loanDto.getLoanType());
        loan.setMobileNumber(loanDto.getMobileNumber());
        loan.setAmountPaid(loanDto.getAmountPaid());
        loan.setOutstandingAmount(loanDto.getOutstandingAmount());

        return loan;

    }

    public static Loan mapToLoan(LoanRequestDto loanRequestDtoDto, Loan loan){

        loan.setLoanType(loanRequestDtoDto.getLoanType());
        loan.setTotalLoan(loanRequestDtoDto.getTotalLoan());
        loan.setMobileNumber(loanRequestDtoDto.getMobileNumber());

        return loan;

    }

    public static LoanDto mapToLoanDto(Loan loan,LoanDto loanDto){

        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setTotalLoan(loan.getTotalLoan());
        loanDto.setLoanType(loan.getLoanType());
        loanDto.setMobileNumber(loan.getMobileNumber());
        loanDto.setAmountPaid(loan.getAmountPaid());
        loanDto.setOutstandingAmount(loan.getOutstandingAmount());

        return loanDto;

    }

}

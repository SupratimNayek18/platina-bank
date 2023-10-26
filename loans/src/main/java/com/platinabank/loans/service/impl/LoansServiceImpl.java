package com.platinabank.loans.service.impl;

import com.platinabank.loans.dto.LoanDto;
import com.platinabank.loans.dto.LoanResponseDto;
import com.platinabank.loans.exception.ResourceNotFoundException;
import com.platinabank.loans.model.Loan;
import com.platinabank.loans.repository.LoansRepository;
import com.platinabank.loans.service.ILoansService;
import com.platinabank.loans.util.IdGenerator;
import com.platinabank.loans.util.mapper.LoanMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
@Transactional
public class LoansServiceImpl implements ILoansService {

    //Constructor Injection
    LoansRepository loansRepository;

    //Method to create loan
    @Override
    public LoanDto createLoan(LoanDto loanDto) {

        Loan loan = LoanMapper.mapToLoan(loanDto, new Loan());
        loan.setLoanId(IdGenerator.generateLoanId());

        Loan savedLoan = loansRepository.save(loan);
        return LoanMapper.mapToLoanDto(savedLoan, new LoanDto());
    }

    //Method to fetch loan details
    @Override
    public LoanDto getLoanDetails(Long loanId) {
        Optional<Loan> loan = loansRepository.findById(loanId);

        if (loan.isEmpty()) {
            throw new ResourceNotFoundException("No loans found for loan id :" + loanId);
        }

        return LoanMapper.mapToLoanDto(loan.get(), new LoanDto());
    }

    //Method to fetch total loan and outstanding amount
    @Override
    public LoanResponseDto getTotalLoanInfo(Long mobileNumber) {
        List<Loan> loanList = loansRepository.findByMobileNumber(mobileNumber);

        AtomicInteger totalLoanAmount = new AtomicInteger();
        AtomicInteger totalOutstandingAmount = new AtomicInteger();

        loanList.forEach(i -> {
            totalLoanAmount.addAndGet(i.getTotalLoan());
            totalOutstandingAmount.addAndGet(i.getOutstandingAmount());
        });

        return new LoanResponseDto(totalLoanAmount.get(),totalOutstandingAmount.get());

    }

    //Method to update loan details
    @Override
    public void updateLoanDetails(Long loanId,int amount) {

        Optional<Loan> optionalLoan = loansRepository.findById(loanId);

        if (optionalLoan.isEmpty()) {
            throw new ResourceNotFoundException("No loans found for loan id :" + loanId);
        }

        Loan loan = optionalLoan.get();
        loan.setOutstandingAmount(loan.getOutstandingAmount()-amount);

        loansRepository.save(loan);

    }

    //Method to delete loan details
    @Override
    public void deleteLoanDetails(Long loanId) {
        Optional<Loan> optionalLoan = loansRepository.findById(loanId);

        if (optionalLoan.isEmpty()) {
            throw new ResourceNotFoundException("No loans found for loan id :" + loanId);
        }

        loansRepository.deleteById(loanId);
    }
}

package com.platinabank.loans;

import com.platinabank.loans.dto.LoanDto;
import com.platinabank.loans.dto.LoanRequestDto;
import com.platinabank.loans.dto.LoanResponseDto;
import com.platinabank.loans.exception.ResourceNotFoundException;
import com.platinabank.loans.model.Loan;
import com.platinabank.loans.repository.LoansRepository;
import com.platinabank.loans.service.impl.LoansServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceLayerTest {

    @InjectMocks
    LoansServiceImpl loansService;

    @Mock
    LoansRepository loansRepository;


    @Test
    public void doCreateLoan() {

        LoanRequestDto loanRequestDto = new LoanRequestDto();
        loanRequestDto.setTotalLoan(1000); // Set any necessary values

        when(loansRepository.save(any(Loan.class))).thenReturn(new Loan());

        // Act
        loansService.createLoan(loanRequestDto);

        // Assertions
        verify(loansRepository, times(1)).save(any(Loan.class));
    }


    @Test
    public void doGetLoanDetailsWhenSuccess() {

        Loan loan = new Loan();
        loan.setLoanId(123L);

        when(loansRepository.findByLoanNumber(123L)).thenReturn(Optional.of(loan)); // Create a method to generate a test loan

        // Action
        LoanDto loanDto = loansService.getLoanDetails(123L);

        // Assertions
        assertNotNull(loanDto);
    }


    @Test
    public void doGetLoanDetailsWhenFailure() {

        when(loansRepository.findByLoanNumber(123L)).thenReturn(Optional.empty()); // Create a method to generate a test loan

        // Assertions
        assertThrows(ResourceNotFoundException.class,()->loansService.getLoanDetails(123L));
    }


    @Test
    public void doGetTotalLoanInfoWhenSuccess() {

        Long mobileNumber = 1234567890L;

        List<Loan> mockLoanList = createTestLoanList(); // Create a method to generate a list of test loans
        when(loansRepository.findByMobileNumber(mobileNumber)).thenReturn(mockLoanList);

        LoanResponseDto loanResponseDto = loansService.getTotalLoanInfo(mobileNumber);

        // Assertions
        assertNotNull(loanResponseDto);
        assertEquals(3000, loanResponseDto.getTotalLoanAmount());
        assertEquals(1500, loanResponseDto.getTotalOutstandingAmount());
    }


    private List<Loan> createTestLoanList() {
        List<Loan> loanList = new ArrayList<>();

        Loan loan1 = new Loan();
        loan1.setTotalLoan(1000);
        loan1.setOutstandingAmount(1000);

        Loan loan2 = new Loan();
        loan2.setTotalLoan(2000);
        loan2.setOutstandingAmount(500);

        loanList.add(loan1);
        loanList.add(loan2);

        return loanList;
    }


    @Test
    public void doUpdateLoanDetailsWhenSuccess() {

        Long loanNumber = 123L;
        int amount = 500;

        Optional<Loan> mockOptionalLoan = Optional.of(createTestLoan()); // Create a method to generate a test loan
        when(loansRepository.findByLoanNumber(loanNumber)).thenReturn(mockOptionalLoan);

        // Action
        boolean result = loansService.updateLoanDetails(loanNumber, amount);

        // Assertions
        assertTrue(result);
        verify(loansRepository, times(1)).save(any(Loan.class));
    }


    @Test
    public void doUpdateLoanDetailsWhenFailure() {

        Long loanNumber = 456L;
        int amount = 500;

        when(loansRepository.findByLoanNumber(loanNumber)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(ResourceNotFoundException.class, () -> loansService.updateLoanDetails(loanNumber, amount));
        // Verifying that save method is not called for non-existing loan
        verify(loansRepository, never()).save(any(Loan.class));
    }


    @Test
    public void doDeleteLoanDetailsWhenSuccess() {

        Long loanNumber = 123L;

        Optional<Loan> mockOptionalLoan = Optional.of(createTestLoan()); // Create a method to generate a test loan
        when(loansRepository.findByLoanNumber(loanNumber)).thenReturn(mockOptionalLoan);

        // Action
        boolean result = loansService.deleteLoanDetails(loanNumber);

        // Assertions
        assertTrue(result);

        verify(loansRepository, times(1)).delete(any(Loan.class));
    }


    @Test
    public void doDeleteLoanDetailsWhenFailure() {

        Long loanNumber = 456L;

        LoansRepository loansRepository = mock(LoansRepository.class);
        when(loansRepository.findByLoanNumber(loanNumber)).thenReturn(Optional.empty());

        // Assertions
        assertThrows(ResourceNotFoundException.class, () -> loansService.deleteLoanDetails(loanNumber));
        // Verifying that delete method is not called for non-existing loan
        verify(loansRepository, never()).delete(any(Loan.class));
    }


    // Helper method to create a test loan for the test cases
    private Loan createTestLoan() {
        Loan loan = new Loan();

        loan.setLoanNumber(123L);
        loan.setOutstandingAmount(1000);

        return loan;
    }







}

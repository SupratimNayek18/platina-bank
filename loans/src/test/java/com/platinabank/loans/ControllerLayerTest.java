package com.platinabank.loans;

import com.platinabank.loans.constants.LoanConstants;
import com.platinabank.loans.controller.LoansController;
import com.platinabank.loans.dto.LoanDto;
import com.platinabank.loans.dto.LoanRequestDto;
import com.platinabank.loans.dto.LoanResponseDto;
import com.platinabank.loans.dto.ResponseDto;
import com.platinabank.loans.service.impl.LoansServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ControllerLayerTest {

    @InjectMocks
    LoansController loansController;

    @Mock
    LoansServiceImpl loansService;


    @Test
    public void doCreateLoanWhenSuccess(){

        LoanRequestDto loanRequestDto = new LoanRequestDto();
        loanRequestDto.setTotalLoan(10000);

        doNothing().when(loansService).createLoan(loanRequestDto);

        ResponseEntity<ResponseDto> response = loansController.createLoan(loanRequestDto);

        assertEquals(HttpStatus.CREATED,response.getStatusCode());

    }


    @Test
    public void doGetLoanDetails(){

        LoanDto loanDto = new LoanDto();
        loanDto.setLoanNumber(7894561238789456L);

        when(loansService.getLoanDetails(7894561238789456L)).thenReturn(loanDto);

        ResponseEntity<LoanDto> response = loansController.getLoanDetails(7894561238789456L);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(loanDto,response.getBody());

    }


    @Test
    public void doGetTotalLoanDetails(){

        LoanResponseDto loanResponseDto = new LoanResponseDto();
        loanResponseDto.setTotalLoanAmount(50000);

        when(loansService.getTotalLoanInfo(4567891237L)).thenReturn(loanResponseDto);

        ResponseEntity<LoanResponseDto> response = loansController.getTotalLoanDetails("wdefd-wdfs-wdasf",4567891237L);

        assertEquals(response.getStatusCode(),HttpStatus.OK);
        assertEquals(response.getBody(),loanResponseDto);
    }


    @Test
    public void doUpdateLoanWhenSuccess() {

        when(loansService.updateLoanDetails(anyLong(), anyInt())).thenReturn(true);

        ResponseEntity<ResponseDto> response = loansController.updateLoan(123L, 500);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LoanConstants.MESSAGE_200, response.getBody().getStatusMsg());

        // Verifying that the updateLoanDetails method was called with the correct parameters
        verify(loansService, times(1)).updateLoanDetails(123L, 500);
    }


    @Test
    public void doUpdateLoanWhenFailure() {

        when(loansService.updateLoanDetails(anyLong(), anyInt())).thenReturn(false);

        ResponseEntity<ResponseDto> response = loansController.updateLoan(456L, 1000);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(LoanConstants.MESSAGE_500, response.getBody().getStatusMsg());

        // Verifying that the updateLoanDetails method was called with the correct parameters
        verify(loansService, times(1)).updateLoanDetails(456L, 1000);
    }


    @Test
    public void doDeleteLoanWhenSuccess() {

        when(loansService.deleteLoanDetails(anyLong())).thenReturn(true);

        ResponseEntity<ResponseDto> response = loansController.deleteLoan(123L);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(LoanConstants.MESSAGE_200, response.getBody().getStatusMsg());

        // Verifying that the deleteLoanDetails method was called with the correct parameter
        verify(loansService, times(1)).deleteLoanDetails(123L);
    }


    @Test
    public void doDeleteLoanWhenFailure() {

        when(loansService.deleteLoanDetails(anyLong())).thenReturn(false);

        ResponseEntity<ResponseDto> response = loansController.deleteLoan(456L);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(LoanConstants.MESSAGE_500, response.getBody().getStatusMsg());

        // Verifying that the deleteLoanDetails method was called with the correct parameter
        verify(loansService, times(1)).deleteLoanDetails(456L);
    }

}

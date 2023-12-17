package com.platinabank.accounts.service.client;

import com.platinabank.accounts.dto.LoanResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{

    @Override
    public ResponseEntity<LoanResponseDto> getTotalLoanDetails(String correlationId, Long mobileNumber) {
        return null;
    }

}

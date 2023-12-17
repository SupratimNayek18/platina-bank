package com.platinabank.accounts.service.client;

import com.platinabank.accounts.dto.LoanResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "platinabank-loans",fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping("/api/loans/getTotalLoanDetails")
    public ResponseEntity<LoanResponseDto> getTotalLoanDetails(@RequestHeader("platinabank-correlation-id") String correlationId, @RequestParam Long mobileNumber);

}

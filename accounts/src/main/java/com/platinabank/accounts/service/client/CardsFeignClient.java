package com.platinabank.accounts.service.client;

import com.platinabank.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "platinabank-cards",fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/cards/getAllCardDetails",consumes = "application/json")
    public ResponseEntity<List<CardDto>> getAllCardDetails(@RequestHeader("platinabank-correlation-id") String correlationId, @RequestParam Long mobileNumber);

}

package com.platinabank.accounts.service.client;

import com.platinabank.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("platinabank-cards")
public interface CardsFeignClient {

    @GetMapping(value = "/api/cards/getAllCardDetails",consumes = "application/json")
    public ResponseEntity<List<CardDto>> getAllCardDetails(@RequestParam Long mobileNumber);

}

package com.platinabank.accounts.service.client;

import com.platinabank.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardsFallback implements CardsFeignClient{

    @Override
    public ResponseEntity<List<CardDto>> getAllCardDetails(String correlationId, Long mobileNumber) {
        return null;
    }

}

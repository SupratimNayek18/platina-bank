package com.platinabank.cards.service;

import com.platinabank.cards.dto.CardDto;
import com.platinabank.cards.dto.CardRequestDto;
import com.platinabank.cards.dto.CardUpdateDto;

import java.util.List;

public interface ICardsService {

    void issueCard(CardRequestDto cardRequestDto);

    CardDto getCardDetails(Long cardNumber);

    List<CardDto> getAllCardDetails(Long mobileNumber);

    boolean updateCard(CardUpdateDto cardUpdateDto);

    boolean deleteCard(Long cardNumber);

}

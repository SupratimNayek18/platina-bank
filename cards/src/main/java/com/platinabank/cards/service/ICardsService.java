package com.platinabank.cards.service;

import com.platinabank.cards.dto.CardDto;
import com.platinabank.cards.dto.CardRequestDto;
import com.platinabank.cards.dto.CardUpdateDto;

import java.util.List;

public interface ICardsService {

    CardDto issueCard(CardRequestDto cardRequestDto);

    CardDto getCardDetails(Long cardNumber);

    List<CardDto> getAllCardDetails(Long mobileNumber);

    void updateCard(CardUpdateDto cardUpdateDto);

    void deleteCard(Long cardNumber);

}

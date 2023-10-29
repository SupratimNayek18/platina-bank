package com.platinabank.cards.util.mapper;

import com.platinabank.cards.dto.CardDto;
import com.platinabank.cards.model.Card;

public class CardMapper {

    private CardMapper(){}

    public static CardDto mapToCardDto(Card card, CardDto cardDto){
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setCardType(card.getCardType());
        cardDto.setTotalLimit(card.getTotalLimit());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        return cardDto;
    }

}

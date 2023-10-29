package com.platinabank.cards.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("cards")
public class Card {

    @Id
    private Long cardId;

    private Long mobileNumber;

    private Long cardNumber;

    private String cardType;

    private int totalLimit;

    private int amountUsed;

    private int availableAmount;

}

package com.platinabank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(
        name = "CardDto",
        description = "Schema to hold card response"
)
public class CardDto {

    @Schema(
            description = "Mobile number of customer", example = "7896541245"
    )
    private Long mobileNumber;

    @Schema(
            description = "Card number of customer", example = "7896541245787856"
    )
    private Long cardNumber;

    @Schema(
            description = "Card type of customer", example = "Debit Card"
    )
    private String cardType;

    @Schema(
            description = "Total Limit of card", example = "31000"
    )
    private int totalLimit;

    @Schema(
            description = "Limit utilized by customer", example = "5000"
    )
    private int amountUsed;

    @Schema(
            description = "Available amount of customer", example = "26000"
    )
    private int availableAmount;

}

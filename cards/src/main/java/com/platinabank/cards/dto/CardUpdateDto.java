package com.platinabank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardUpdateDto {

    @NotNull
    @Schema(
            description = "Card number of customer", example = "7896541236789654"
    )
    private Long cardNumber;

    @Schema(
            description = "Total limit of customer", example = "31000"
    )
    private int totalLimit;

    @Schema(
            description = "Amount used by customer", example = "5000"
    )
    private int amountUsed;

}

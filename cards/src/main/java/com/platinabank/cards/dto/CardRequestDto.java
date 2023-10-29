package com.platinabank.cards.dto;

import com.platinabank.cards.util.mobileNumberValidator.ValidMobileNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardRequestDto {

    @ValidMobileNumber
    @Schema(
            description = "Mobile number of customer", example = "7896541236"
    )
    private Long mobileNumber;

    @NotEmpty(message = "CardType can not be a null or empty")
    @Schema(
            description = "Type of the card", example = "Credit Card"
    )
    private String cardType;

}

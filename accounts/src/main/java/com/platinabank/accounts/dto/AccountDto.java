package com.platinabank.accounts.dto;

import com.platinabank.accounts.util.validators.accountNumberValidator.ValidAccountNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(
        name = "Account",
        description = "Schema to hold account information"
)
public class AccountDto {

    @Schema(
            description = "Account number of customer"
    )
    @ValidAccountNumber
    private long accountNumber;

    @Schema(
            description = "Account balance of the customer"
    )
    @PositiveOrZero(message = "Amount must be a positive value")
    private double amount;

    @Schema(
            description = "Account type of the customer",
            example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be empty")
    private String accountType;

    @Schema(
            description = "Branch Address of the customer"
    )
    @NotEmpty(message = "Branch address cannot be empty")
    private String branchAddress;

    @Valid
    private CustomerDto customerDto;

}

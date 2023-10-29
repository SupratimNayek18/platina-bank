package com.platinabank.loans.dto;

import com.platinabank.loans.util.validator.mobileNumberValidator.ValidMobileNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(
        name = "LoanRequest",
        description = "Schema to hold total loan request"
)
public class LoanRequestDto {

    @ValidMobileNumber
    @Schema(
            description = "Mobile Number of Customer", example = "4365327698"
    )
    private Long mobileNumber;

    @NotEmpty(message = "LoanType can not be a null or empty")
    @Schema(
            description = "Type of the loan", example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(
            description = "Total loan amount", example = "100000"
    )
    private int totalLoan;

}

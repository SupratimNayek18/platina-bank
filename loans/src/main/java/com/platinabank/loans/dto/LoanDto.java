package com.platinabank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "LoanDto",
        description = "Schema to hold loan response"
)
public class LoanDto {

    @Schema(
            description = "Mobile number of the customer",example = "7699091004"
    )
    private Long mobileNumber;

    @Schema(
            description = "12 digit loan number of the loan",example = "789456123456"
    )
    private Long loanNumber;

    @Schema(
            description = "Loan type of the loan",example = "Home Loan"
    )
    private String loanType;

    @Schema(
            description = "Loan amount sanctioned",example = "500000"
    )
    private int totalLoan;

    @Schema(
            description = "Amount paid by the customer",example = "40000"
    )
    private int amountPaid;

    @Schema(
            description = "Amount left to be paid by the customer",example = "50000"
    )
    private int outstandingAmount;

}

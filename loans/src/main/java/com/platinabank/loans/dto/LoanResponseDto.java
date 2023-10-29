package com.platinabank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "LoanResponse",
        description = "Schema to hold total loan response"
)
public class LoanResponseDto {

    @Schema(
            description = "Total amount of loan taken", example = "40000000"
    )
    int totalLoanAmount;

    @Schema(
            description = "Total outstanding amount to be paid", example = "20000000"
    )
    int totalOutstandingAmount;

}

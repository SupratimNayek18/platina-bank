package com.platinabank.loans.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDto {

    int totalLoanAmount;

    int totalOutstandingAmount;

}

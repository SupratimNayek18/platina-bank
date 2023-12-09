package com.platinabank.accounts.dto;

import com.platinabank.accounts.util.validators.mobileNumberValidator.ValidMobileNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(
        name = "Customer Details",
        description = "Schema to hold customer,cards,loans details"
)
public class CustomerDetailsDto {


    @Schema(
            description = "First name of customer",
            example = "Supratim"
    )
    @NotEmpty(message = "First name cannot be null")
    @Size(min = 3,max = 15,message = "First name length must be between 5 and 15")
    private String firstName;

    @Schema(
            description = "Last name of customer",
            example = "Nayek"
    )
    @NotEmpty(message = "Last name cannot be null")
    @Size(min = 3,max = 15,message = "Last name length must be between 5 and 15")
    private String lastName;

    @Schema(
            description = "Email of customer",
            example = "abc@example.com"
    )
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Should be a valid email id")
    private String email;

    @Schema(
            description = "Mobile Number of customer",
            example = "8965412657"
    )
    @ValidMobileNumber
    private Long mobileNumber;

    @Schema(
            description = "Account information of the customer"
    )
    private AccountDto accountDto;

    @Schema(
            description = "Cards information of the customer"
    )
    private List<CardDto> cardDtoList;

    @Schema(
            description = "Consolidated loans information of the customer"
    )
    private LoanResponseDto loanResponseDto;


}

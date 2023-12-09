package com.platinabank.accounts.controller;

import com.platinabank.accounts.dto.CustomerDetailsDto;
import com.platinabank.accounts.dto.ErrorResponseDto;
import com.platinabank.accounts.service.ICustomerService;
import com.platinabank.accounts.util.validators.mobileNumberValidator.ValidMobileNumber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "REST APIs for complete information of customer in Platina Bank"
)
@AllArgsConstructor
public class CustomerController {

    private ICustomerService customerService;

    @Operation(
            summary = "GET API endpoint for getting customer details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestParam @ValidMobileNumber long mobileNumber){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerDetails(mobileNumber));
    }

}

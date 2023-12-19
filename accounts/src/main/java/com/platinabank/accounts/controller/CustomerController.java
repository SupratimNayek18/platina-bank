package com.platinabank.accounts.controller;

import com.platinabank.accounts.dto.CustomerDetailsDto;
import com.platinabank.accounts.dto.ErrorResponseDto;
import com.platinabank.accounts.service.ICustomerService;
import com.platinabank.accounts.util.validators.mobileNumberValidator.ValidMobileNumber;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "REST APIs for complete information of customer in Platina Bank"
)
@AllArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

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
    @RateLimiter(name = "getCustomerDetails",fallbackMethod = "getCustomerDetailsFallBack")
    @GetMapping("/getCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(
            @RequestHeader("platinabank-correlation-id") String correlationId,
            @RequestParam @ValidMobileNumber long mobileNumber){
        logger.debug("platinabank-correlation-id found : {}",correlationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerDetails(mobileNumber,correlationId));
    }

    public ResponseEntity<CustomerDetailsDto> getCustomerDetailsFallBack(Throwable throwable){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

}

package com.platinabank.accounts.controller;

import com.platinabank.accounts.constants.AccountsConstants;
import com.platinabank.accounts.dto.*;
import com.platinabank.accounts.service.IAccountsService;
import com.platinabank.accounts.util.validators.customerIdValidator.ValidCustomerId;
import com.platinabank.accounts.util.validators.mobileNumberValidator.ValidMobileNumber;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD REST APIs for Accounts in Platina Bank"
)
public class AccountsController {

    private IAccountsService accountsService;

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

    AccountsController(IAccountsService accountsService){
        this.accountsService = accountsService;
    }

    @Autowired
    private ConfigProperties configProperties;

    //Post endpoint to create customer account
    @Operation(
            summary = "Post API endpoint for creating accounts"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status created"
    )
    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        //Invoking the create account method of account service
        accountsService.createAccount(customerDto);

        //Returning success response if no exception occurs
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }


    //Get endpoint for fetching account details using mobile number
    @Operation(
            summary = "GET API endpoint for getting account details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/getAccountDetails")
    public ResponseEntity<AccountDto> getAccountDetails(@RequestParam @ValidMobileNumber long mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsService.getAccountDetails(mobileNumber));
    }


    //Put endpoint to update account and customer details
    @Operation(
            summary = "Put API endpoint for updating account details"
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
    @PutMapping("/updateAccountDetails")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody AccountDto accountDto) {
        boolean isUpdated = accountsService.updateAccount(accountDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }


    //Delete endpoint to delete account and customer details
    @Operation(
            summary = "Delete API endpoint for updating account details"
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
    @DeleteMapping("/deleteAccountDetails")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @ValidCustomerId
                                                            int customerId) {
        boolean isDeleted = accountsService.deleteAccount(customerId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }


    @Operation(
            summary = "Config API endpoint for getting config details"
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
    @Retry(name = "getConfigRetry",fallbackMethod = "getConfigFallback")
    @GetMapping("/getConfig")
    public ResponseEntity<ConfigProperties> getConfig() throws TimeoutException {
        logger.debug("getConfig() called");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(configProperties);
    }

    public ResponseEntity<ConfigProperties> getConfigFallback(Throwable throwable){
        logger.debug("getConfigFallback() called");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

}

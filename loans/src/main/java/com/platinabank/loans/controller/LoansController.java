package com.platinabank.loans.controller;

import com.platinabank.loans.constants.LoanConstants;
import com.platinabank.loans.dto.*;
import com.platinabank.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(
        name = "CRUD rest apis for Loans Microservice"
)
@RequestMapping("/loans")
public class LoansController {

    private ILoansService loansService;


    @Operation(
            summary = "Post API endpoint for creating loan"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status created"
    )
    @PostMapping("/createLoan")
    public ResponseEntity<ResponseDto> createLoan(@RequestBody @Valid LoanRequestDto loanRequestDto){
        loansService.createLoan(loanRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoanConstants.STATUS_201,LoanConstants.MESSAGE_201));
    }


    @Operation(
            summary = "Get API endpoint for getting loan details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/getLoanDetails")
    public ResponseEntity<LoanDto> getLoanDetails(@RequestParam Long loanNumber){
        LoanDto loanDto = loansService.getLoanDetails(loanNumber);
        return ResponseEntity
                .ok()
                .body(loanDto);
    }


    @Operation(
            summary = "Get API endpoint for getting all loan summary amount and outstanding"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/getTotalLoanDetails")
    public ResponseEntity<LoanResponseDto> getTotalLoanDetails(@RequestParam Long mobileNumber){
        LoanResponseDto loanResponseDto = loansService.getTotalLoanInfo(mobileNumber);
        return ResponseEntity
                .ok()
                .body(loanResponseDto);
    }


    @Operation(
            summary = "Put API endpoint for updating/paying loan amount"
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
    @PutMapping("/updateLoan")
    public ResponseEntity<ResponseDto> updateLoan(@RequestParam Long loanNumber,@RequestParam int amount){
        boolean status = loansService.updateLoanDetails(loanNumber,amount);
        if(status){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(LoanConstants.STATUS_500, LoanConstants.MESSAGE_500));
        }
    }


    @Operation(
            summary = "Delete API endpoint for deleting loan details"
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
    @DeleteMapping("/deleteLoan")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam Long loanNumber){
        boolean status = loansService.deleteLoanDetails(loanNumber);
        if(status){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(LoanConstants.STATUS_500, LoanConstants.MESSAGE_500));
        }
    }


}

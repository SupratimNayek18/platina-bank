package com.platinabank.cards.controller;

import com.platinabank.cards.constants.AccountsConstants;
import com.platinabank.cards.dto.*;
import com.platinabank.cards.service.ICardsService;
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

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cards")
@Tag(
        name = "CRUD rest apis for Cards Microservice"
)
public class CardsController {

    private ICardsService cardsService;


    @Operation(
            summary = "Post API endpoint for issuing card"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status created"
    )
    @PostMapping("/issueCard")
    public ResponseEntity<CardDto> issueCard(@RequestBody @Valid CardRequestDto cardRequestDto){
        CardDto cardDto = cardsService.issueCard(cardRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cardDto);
    }


    @Operation(
            summary = "Get API endpoint for getting card details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/getCardDetails")
    public ResponseEntity<CardDto> getCardDetails(@RequestParam Long cardNumber){
        CardDto cardDto = cardsService.getCardDetails(cardNumber);
        return ResponseEntity
                .ok()
                .body(cardDto);
    }


    @Operation(
            summary = "Get API endpoint for getting all card details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/getAllCardDetails")
    public ResponseEntity<List<CardDto>> getAllCardDetails(@RequestParam Long mobileNumber){
        List<CardDto> cardList = cardsService.getAllCardDetails(mobileNumber);
        return ResponseEntity
                .ok()
                .body(cardList);
    }


    @Operation(
            summary = "Put API endpoint for updating/paying card limit/paid/outstanding amount"
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
    @PutMapping("/updateCard")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody @Valid CardUpdateDto cardUpdateDto){
        cardsService.updateCard(cardUpdateDto);
        return ResponseEntity
                .ok()
                .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
    }


    @Operation(
            summary = "Delete API endpoint for deleting card details"
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
    @DeleteMapping("/deleteCard")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam Long cardNumber){
        cardsService.deleteCard(cardNumber);
        return ResponseEntity
                .ok()
                .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
    }

}

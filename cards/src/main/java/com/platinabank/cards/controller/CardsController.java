package com.platinabank.cards.controller;

import com.platinabank.cards.constants.CardsConstants;
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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@Tag(
        name = "CRUD rest apis for Cards Microservice"
)
public class CardsController {

    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    private ICardsService cardsService;

    CardsController(ICardsService cardsService) {
        this.cardsService = cardsService;
    }

    @Autowired
    private ConfigProperties configProperties;


    @Operation(
            summary = "Post API endpoint for issuing card"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status created"
    )
    @PostMapping("/issueCard")
    public ResponseEntity<ResponseDto> issueCard(@RequestBody @Valid CardRequestDto cardRequestDto) {
        cardsService.issueCard(cardRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }


    @Operation(
            summary = "Get API endpoint for getting card details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/getCardDetails")
    public ResponseEntity<CardDto> getCardDetails(@RequestParam Long cardNumber) {
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
    public ResponseEntity<List<CardDto>> getAllCardDetails(@RequestHeader("platinabank-correlation-id") String correlationId, @RequestParam Long mobileNumber) {
        logger.debug("platinabank-correlation-id found : {}",correlationId);
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
    public ResponseEntity<ResponseDto> updateCard(@RequestBody @Valid CardUpdateDto cardUpdateDto) {
        boolean status = cardsService.updateCard(cardUpdateDto);
        if (status) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_500, CardsConstants.MESSAGE_500));
        }

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
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam Long cardNumber) {

        boolean status = cardsService.deleteCard(cardNumber);

        if (status) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(CardsConstants.STATUS_500, CardsConstants.MESSAGE_500));
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
    @GetMapping("/getConfig")
    public ResponseEntity<ConfigProperties> getConfig() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(configProperties);
    }

}

package com.platinabank.cards;

import com.platinabank.cards.constants.CardsConstants;
import com.platinabank.cards.controller.CardsController;
import com.platinabank.cards.dto.CardDto;
import com.platinabank.cards.dto.CardRequestDto;
import com.platinabank.cards.dto.CardUpdateDto;
import com.platinabank.cards.dto.ResponseDto;
import com.platinabank.cards.service.impl.CardsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ControllerLayerTest {

    @InjectMocks
    private CardsController cardsController;

    @Mock
    private CardsServiceImpl cardsService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void doTestIssueCardWhenSuccess(){
        CardRequestDto cardRequestDto = new CardRequestDto();
        cardRequestDto.setCardType("Debit");

        doNothing().when(cardsService).issueCard(cardRequestDto);

        ResponseEntity<ResponseDto> response = cardsController.issueCard(cardRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(CardsConstants.STATUS_201, response.getBody().getStatusCode());
        assertEquals(CardsConstants.MESSAGE_201, response.getBody().getStatusMsg());

    }


    @Test
    public void doGetCardDetailsWhenFound(){

        CardDto cardDto = new CardDto();
        cardDto.setCardNumber(1234567898964125L);

        when(cardsService.getCardDetails(1234567898964125L)).thenReturn(cardDto);

        ResponseEntity<CardDto> response = cardsController.getCardDetails(1234567898964125L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardDto, response.getBody());

    }


    @Test
    public void doGetAllCardDetailsWhenFound(){
        CardDto cardDto1 = new CardDto();
        cardDto1.setCardNumber(1234567898964125L);

        CardDto cardDto2 = new CardDto();
        cardDto2.setCardNumber(1234567898964125L);

        List<CardDto> cardList = new ArrayList<>();
        cardList.add(cardDto1);
        cardList.add(cardDto2);

        when(cardsService.getAllCardDetails(7699091004L)).thenReturn(cardList);

        ResponseEntity<List<CardDto>> response = cardsController.getAllCardDetails(7699091004L);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(cardList,response.getBody());
    }


    @Test
    public void doUpdateCardWhenSuccess(){

        CardUpdateDto cardUpdateDto = new CardUpdateDto();
        cardUpdateDto.setCardNumber(1234567898964125L);
        cardUpdateDto.setTotalLimit(20000);

        when(cardsService.updateCard(cardUpdateDto)).thenReturn(true);

        ResponseEntity<ResponseDto> response = cardsController.updateCard(cardUpdateDto);

        assertEquals(response.getStatusCode(),HttpStatus.OK);
        assertEquals(response.getBody().getStatusMsg(),CardsConstants.MESSAGE_200);

    }


    @Test
    public void doUpdateCardWhenFailure(){

        CardUpdateDto cardUpdateDto = new CardUpdateDto();
        cardUpdateDto.setCardNumber(1234567898964125L);
        cardUpdateDto.setTotalLimit(20000);

        when(cardsService.updateCard(cardUpdateDto)).thenReturn(false);

        ResponseEntity<ResponseDto> response = cardsController.updateCard(cardUpdateDto);

        assertEquals(response.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody().getStatusMsg(),CardsConstants.MESSAGE_500);

    }


    @Test
    public void doDeleteCardWhenSuccess(){

        when(cardsService.deleteCard(1234567891234567L)).thenReturn(true);

        ResponseEntity<ResponseDto> response = cardsController.deleteCard(1234567891234567L);

        assertEquals(response.getStatusCode(),HttpStatus.OK);
        assertEquals(response.getBody().getStatusMsg(),CardsConstants.MESSAGE_200);

    }


    @Test
    public void doDeleteCardWhenFailure(){

        when(cardsService.deleteCard(1234567891234567L)).thenReturn(false);

        ResponseEntity<ResponseDto> response = cardsController.deleteCard(1234567891234567L);

        assertEquals(response.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(response.getBody().getStatusMsg(),CardsConstants.MESSAGE_500);

    }

}

package com.platinabank.cards;

import com.platinabank.cards.dto.CardDto;
import com.platinabank.cards.dto.CardRequestDto;
import com.platinabank.cards.dto.CardUpdateDto;
import com.platinabank.cards.exception.ResourceNotFoundException;
import com.platinabank.cards.model.Card;
import com.platinabank.cards.repository.CardsRepository;
import com.platinabank.cards.service.impl.CardsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceLayerTest {

    @InjectMocks
    private CardsServiceImpl cardsService;

    @Mock
    private CardsRepository cardsRepository;


    @Test
    public void doIssueCreditCard() {

        CardRequestDto cardRequestDto = new CardRequestDto();
        cardRequestDto.setMobileNumber(1234567890L);
        cardRequestDto.setCardType("Credit Card");

        // Mock repository save
        when(cardsRepository.save(any(Card.class))).thenReturn(new Card());

        // Call the method
        cardsService.issueCard(cardRequestDto);

        // Verify that the save method was called
        verify(cardsRepository, times(1)).save(any(Card.class));

    }


    @Test
    public void doGetCardDetailsWhenFound() {
        // Positive Test Case
        Long cardNumber = 1234567890L;
        Card mockCard = new Card();
        mockCard.setCardNumber(cardNumber);

        // Mocking repository method
        when(cardsRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(mockCard));

        // Calling the method
        CardDto cardDto = cardsService.getCardDetails(cardNumber);

        // Verifying that the repository method was called
        verify(cardsRepository, times(1)).findByCardNumber(cardNumber);

        // Verifying that the returned CardDto matches the mocked Card
        assertEquals(mockCard.getCardNumber(), cardDto.getCardNumber());
    }


    @Test
    public void doGetCardDetailsWhenNotFound() {
        // Negative Test Case - Card not found
        Long nonExistentCardNumber = 9876543210L;

        // Mocking repository method
        when(cardsRepository.findByCardNumber(nonExistentCardNumber)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->cardsService.getCardDetails(nonExistentCardNumber));

    }


    @Test
    public void doGetAllCardDetailsWhenFound() {
        // Positive Test Case - Cards linked to the mobile number
        Long mobileNumber = 1234567890L;
        Card card1 = new Card();
        card1.setMobileNumber(mobileNumber);
        card1.setCardNumber(1111111111L);

        Card card2 = new Card();
        card2.setMobileNumber(mobileNumber);
        card2.setCardNumber(2222222222L);

        List<Card> mockCardList = Arrays.asList(card1, card2);

        // Mocking repository method
        when(cardsRepository.findByMobileNumber(mobileNumber)).thenReturn(mockCardList);

        // Calling the method
        List<CardDto> cardDtoList = cardsService.getAllCardDetails(mobileNumber);

        // Verifying that the repository method was called
        verify(cardsRepository, times(1)).findByMobileNumber(mobileNumber);

        // Verifying that the returned CardDto list size matches the size of the mocked Card list
        assertEquals(mockCardList.size(), cardDtoList.size());
    }


    @Test
    public void testGetAllCardDetailsNoCardsFound() {
        // Negative Test Case - No cards linked to the mobile number
        Long nonExistentMobileNumber = 9876543210L;

        // Mocking repository method
        when(cardsRepository.findByMobileNumber(nonExistentMobileNumber)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,()->cardsService.getAllCardDetails(nonExistentMobileNumber));
    }


    @Test
    public void doUpdateCardTotalLimit() {
        // Positive Test Case - Update total limit
        long cardNumber = 1234567890L;
        CardUpdateDto cardUpdateDto = new CardUpdateDto();
        cardUpdateDto.setCardNumber(cardNumber);
        cardUpdateDto.setTotalLimit(50000);

        Card existingCard = new Card();
        existingCard.setCardNumber(cardNumber);

        // Mock repository method
        when(cardsRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(existingCard));

        // Call the method
        boolean result = cardsService.updateCard(cardUpdateDto);

        // Verify that the repository method was called
        verify(cardsRepository, times(1)).findByCardNumber(cardNumber);
        verify(cardsRepository, times(1)).save(existingCard);

        // Verify that the update was successful
        assertTrue(result);
        assertEquals(cardUpdateDto.getTotalLimit(), existingCard.getTotalLimit());

    }


    @Test
    public void doUpdateCardAmountUsed() {
        // Positive Test Case - Update amount used
        long cardNumber = 1234567890L;
        CardUpdateDto cardUpdateDto = new CardUpdateDto();
        cardUpdateDto.setCardNumber(cardNumber);
        cardUpdateDto.setAmountUsed(1000);

        Card existingCard = new Card();
        existingCard.setCardNumber(cardNumber);
        existingCard.setTotalLimit(50000);
        existingCard.setAvailableAmount(49000);

        // Mock repository method
        when(cardsRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(existingCard));

        // Call the method
        boolean result = cardsService.updateCard(cardUpdateDto);

        // Verify that the repository method was called
        verify(cardsRepository, times(1)).findByCardNumber(cardNumber);
        verify(cardsRepository, times(1)).save(existingCard);

        // Verify that the update was successful
        assertTrue(result);
        assertEquals(existingCard.getAmountUsed(), 1000);
        assertEquals(existingCard.getAvailableAmount(), 48000);
    }


    @Test
    public void doUpdateCardWhenNotFound() {
        // Negative Test Case - Card not found
        long nonExistentCardNumber = 9876543210L;
        CardUpdateDto cardUpdateDto = new CardUpdateDto();
        cardUpdateDto.setCardNumber(nonExistentCardNumber);

        // Mocking repository method
        when(cardsRepository.findByCardNumber(nonExistentCardNumber)).thenReturn(Optional.empty());

        // Calling the method, expecting ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,()->cardsService.updateCard(cardUpdateDto));
    }


    @Test
    public void doDeleteCardWhenSuccess() {
        // Positive Test Case - Delete card
        long cardNumber = 1234567890L;

        Card existingCard = new Card();
        existingCard.setCardNumber(cardNumber);

        // Mock repository method
        when(cardsRepository.findByCardNumber(cardNumber)).thenReturn(Optional.of(existingCard));

        // Call the method
        boolean result = cardsService.deleteCard(cardNumber);

        // Verify that the repository method was called
        verify(cardsRepository, times(1)).findByCardNumber(cardNumber);
        verify(cardsRepository, times(1)).delete(existingCard);

        // Verify that the deletion was successful
        assertTrue(result);
    }


    @Test
    public void doDeleteCardWhenNotFound() {
        // Negative Test Case - Card not found
        long nonExistentCardNumber = 9876543210L;

        // Mock repository method
        when(cardsRepository.findByCardNumber(nonExistentCardNumber)).thenReturn(Optional.empty());

        // Calling the method, expecting ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class,()->cardsService.deleteCard(nonExistentCardNumber));
    }

}

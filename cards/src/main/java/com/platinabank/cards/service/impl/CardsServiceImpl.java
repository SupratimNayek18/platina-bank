package com.platinabank.cards.service.impl;

import com.platinabank.cards.dto.CardDto;
import com.platinabank.cards.dto.CardRequestDto;
import com.platinabank.cards.dto.CardUpdateDto;
import com.platinabank.cards.exception.ResourceNotFoundException;
import com.platinabank.cards.model.Card;
import com.platinabank.cards.repository.CardsRepository;
import com.platinabank.cards.service.ICardsService;
import com.platinabank.cards.util.IdGenerator;
import com.platinabank.cards.util.mapper.CardMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;


    //Method to issue new card to customer
    @Override
    public CardDto issueCard(CardRequestDto cardRequestDto) {

        Card card = new Card();

        card.setMobileNumber(cardRequestDto.getMobileNumber());
        card.setCardId(IdGenerator.generateCardId());
        card.setCardNumber(IdGenerator.generateCardNumber());
        card.setCardType(cardRequestDto.getCardType());
        if(card.getCardType().equalsIgnoreCase("Credit Card")){
            card.setTotalLimit(31000);
            card.setAmountUsed(0);
            card.setAvailableAmount(31000);
        }

        Card savedCard = cardsRepository.save(card);

        return CardMapper.mapToCardDto(savedCard,new CardDto());
    }


    //Method to get card details
    @Override
    public CardDto getCardDetails(Long cardNumber) {

        Optional<Card> optionalCard = cardsRepository.findByCardNumber(cardNumber);

        if(optionalCard.isEmpty()){
            throw new ResourceNotFoundException("Card with number : "+cardNumber+" not found");
        }

        return CardMapper.mapToCardDto(optionalCard.get(),new CardDto());
    }


    //Method to get all card details
    @Override
    public List<CardDto> getAllCardDetails(Long mobileNumber) {

        List<Card> cardList =  cardsRepository.findByMobileNumber(mobileNumber);

        if(cardList.isEmpty()){
            throw new ResourceNotFoundException("No cards linked with mobile number : "+mobileNumber);
        }

        return cardList.stream().map(i-> CardMapper.mapToCardDto(i,new CardDto())).toList();
    }


    //Method to update card amounts
    @Override
    public void updateCard(CardUpdateDto cardUpdateDto) {

        Optional<Card> optionalCard = cardsRepository.findByCardNumber(cardUpdateDto.getCardNumber());

        if(optionalCard.isEmpty()){
            throw new ResourceNotFoundException("Card with card number : "+cardUpdateDto.getCardNumber()+" not found");
        }

        Card card = optionalCard.get();

        if(cardUpdateDto.getTotalLimit()!=0){
            card.setTotalLimit(cardUpdateDto.getTotalLimit());
        }
        else{
            card.setAmountUsed(cardUpdateDto.getAmountUsed()+card.getAmountUsed());
            card.setAvailableAmount(card.getAvailableAmount()-cardUpdateDto.getAmountUsed());
        }

        cardsRepository.save(card);
    }


    //Method to delete card details
    @Override
    public void deleteCard(Long cardNumber) {

        Optional<Card> optionalCard = cardsRepository.findByCardNumber(cardNumber);

        if(optionalCard.isEmpty()){
            throw new ResourceNotFoundException("Card with card number : "+cardNumber+" not found");
        }

        cardsRepository.delete(optionalCard.get());
    }

}

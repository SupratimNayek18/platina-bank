package com.platinabank.cards;

import com.platinabank.cards.model.Card;
import com.platinabank.cards.repository.CardsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class RepositoryLayerTest {

    @Autowired
    CardsRepository cardsRepository;


    @Test
    public void doTestFindByCardNumber(){

        Card card = new Card();
        card.setCardId(100000000000L);
        card.setCardNumber(1234567891234567L);

        cardsRepository.save(card);

        assertNotNull(cardsRepository.findByCardNumber(1234567891234567L).get());
        assertEquals(card.getCardNumber(),cardsRepository.findByCardNumber(1234567891234567L).get().getCardNumber());

        cardsRepository.delete(card);

    }


    @Test
    public void doTestFindByMobileNumber(){

        Card card = new Card();
        card.setCardId(100000000000L);
        card.setCardNumber(1234567891234567L);
        card.setMobileNumber(7699091004L);

        cardsRepository.save(card);

        List<Card> list = cardsRepository.findByMobileNumber(7699091004L);

        assertNotEquals(cardsRepository.findByMobileNumber(7699091004L).size(),0);
        assertEquals(list.get(0).getMobileNumber(),7699091004L);

        cardsRepository.delete(card);

    }

}

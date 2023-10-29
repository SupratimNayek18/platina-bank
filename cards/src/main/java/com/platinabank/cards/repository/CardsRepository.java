package com.platinabank.cards.repository;

import com.platinabank.cards.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends MongoRepository<Card,Long> {

    Optional<Card> findByCardNumber(Long cardNumber);

    List<Card> findByMobileNumber(Long mobileNumber);

}

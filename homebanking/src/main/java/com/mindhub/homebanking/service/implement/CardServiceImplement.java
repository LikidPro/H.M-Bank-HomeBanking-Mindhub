package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepositort;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepositort cardRepositort;


    @Override
    public void cardSave(Card card) {
        cardRepositort.save(card);
    }

    @Override
    public Card getCardById(long id) {
        return cardRepositort.findById(id).orElse(null);
    }
    @Override
    public void deleteCard(Card card) {
        cardRepositort.delete(card);
    }

    @Override
    public void disabledCard(long id) {
      Card card=cardRepositort.findById(id).orElse(null);
      card.setCardState(false);
      cardRepositort.save(card);
    }
    public void enableCard(long id) {
        Card card=cardRepositort.findById(id).orElse(null);
        card.setCardState(true);
        cardRepositort.save(card);
    }

    @Override
    public Set<CardDTO> cardsDisabled() {
        return cardRepositort.findAll().stream().filter(card -> card.isCardState()== false).map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }
}

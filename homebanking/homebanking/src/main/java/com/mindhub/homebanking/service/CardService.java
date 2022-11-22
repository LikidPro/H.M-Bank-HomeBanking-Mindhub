package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepositort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public interface CardService {
 public void cardSave(Card card);

 public Card getCardById(long id);

 public void deleteCard(Card card);

public  void  disabledCard(long id);

public  void  enableCard(long id);

public Set<CardDTO> cardsDisabled();
}

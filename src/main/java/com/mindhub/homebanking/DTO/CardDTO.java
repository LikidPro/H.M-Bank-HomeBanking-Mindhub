package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private CardType cardType;
    private String number;
    private int cvv;
    private String cardHolder;
    private LocalDate fromDate, ThruDate;
    private CardColor cardColor;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardType = card.getCardType();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.cardHolder = card.getCardHolder();
        this.fromDate = card.getFromDate();
        this.ThruDate = card.getThruDate();
        this.cardColor = card.getCardColor();
    }

    public long getId() {return id;}

    public CardType getCardType() {return cardType;}

    public String getNumber() {return number;}

    public int getCvv() {return cvv;}

    public String getCardHolder() {return cardHolder;}

    public LocalDate getFromDate() {return fromDate;}

    public LocalDate getThruDate() {return ThruDate;}

    public CardColor getCardColor() {return cardColor;}
}

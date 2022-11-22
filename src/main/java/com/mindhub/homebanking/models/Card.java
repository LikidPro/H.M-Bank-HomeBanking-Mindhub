package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private CardType cardType;
    private String number;
    private int cvv;
    private String cardHolder;
    private LocalDate fromDate, ThruDate;
    private CardColor cardColor;

    private boolean cardState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;



    public Card() {
    }

    public Card(CardType cardType, String number, int cvv, String cardHolder, LocalDate fromDate, LocalDate thruDate, CardColor cardColor) {

        this.cardType = cardType;
        this.number = number;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.fromDate = fromDate;
        this.ThruDate = thruDate;
        this.cardColor = cardColor;
        this.cardState = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {this.id = id;}

    public CardType getCardType() {return cardType;}

    public void setCardType(CardType cardType) {this.cardType = cardType;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public int getCvv() {return cvv;}

    public void setCvv(int cvv) {this.cvv = cvv;}

    public String getCardHolder() {return cardHolder;}

    public void setCardHolder(String cardHolder) {this.cardHolder = cardHolder;}

    public LocalDate getFromDate() {return fromDate;}

    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

    public LocalDate getThruDate() {return ThruDate;}

    public void setThruDate(LocalDate thruDate) {ThruDate = thruDate;}

    public CardColor getCardColor() {return cardColor;}

    public void setCardColor(CardColor cardColor) {this.cardColor = cardColor;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public boolean isCardState() {return cardState;}

    public void setCardState(boolean cardState) {this.cardState = cardState;}
}

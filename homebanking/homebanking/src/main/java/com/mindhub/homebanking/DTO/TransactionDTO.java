package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

public class TransactionDTO {
    private long id;
    private TransactionType type;
    private Double amonunt;
    private String description;
    private String date;

    private Double ballance;


    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amonunt = transaction.getAmonunt();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.ballance= transaction.getBallance();

    }

    public long getId() { return id; }

    public TransactionType getType() { return type; }

    public Double getAmonunt() { return amonunt; }

    public String getDescription() { return description; }

    public String getDate() { return date; }

    public Double getBallance() {return ballance;}
}

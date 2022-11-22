package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private long id;
    private TransactionType type;
    private Double amonunt;
    private String description;
    private String date;

    private Double ballance;

    private boolean transactionState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account ;


    public Transaction() {
    }

    public Transaction( TransactionType type, Double amonunt, String description, String date, Double ballance) {
        this.type = type;
        this.amonunt = amonunt;
        this.description = description;
        this.date = date;
        this.ballance = ballance;
        this.transactionState = true;

    }
    public Transaction( TransactionType type, Double amonunt, String description, String date) {
        this.type = type;
        this.amonunt = amonunt;
        this.description = description;
        this.date = date;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmonunt() {
        return amonunt;
    }

    public void setAmonunt(Double amonunt) {
        this.amonunt = amonunt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAccount(Account account) {this.account = account;}

    public Account getAccount() {return account;}

    public Double getBallance() {return ballance;}

    public void setBallance(Double ballance) {this.ballance = ballance;}

    public boolean isTransactionState() {return transactionState;}

    public void setTransactionState(boolean transactionState) {this.transactionState = transactionState;}

    @Override
    public String toString() {
        return "" + id +

                "  " + type +
                "  " + amonunt +
                "  " + description + '\'' +
                "  " + date
                ;
    }
}

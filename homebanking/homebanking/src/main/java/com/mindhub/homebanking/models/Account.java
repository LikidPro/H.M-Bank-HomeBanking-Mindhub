package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import jdk.internal.jimage.ImageStrings;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double ballance;

    private boolean accountState;

    private AccountType accountType;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch= FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();


    public Account() {
    }

    public Account(String number, LocalDateTime creationDate, double ballance) {
        this.number = number;
        this.creationDate = creationDate;
        this.ballance = ballance;
        this.accountState=true;


    }
    public Account(String number, LocalDateTime creationDate, double ballance,AccountType accountType) {
        this.number = number;
        this.creationDate = creationDate;
        this.ballance = ballance;
        this.accountState=true;
        this.accountType= accountType;


    }
    public void addtransaction( Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBallance() {
        return ballance;
    }

    public void setBallance(double ballance) {
        this.ballance = ballance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isAccountState() {return accountState;}

    public void setAccountState(boolean accountState) {this.accountState = accountState;}

    public void setTransactions(Set<Transaction> transactions) {this.transactions = transactions;}


    public AccountType getAccountType() {return accountType;}

    public void setAccountType(AccountType accountType) {this.accountType = accountType;}

    @Override
    public String toString() {
        return "account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", ballance=" + ballance +
                '}';
    }

}

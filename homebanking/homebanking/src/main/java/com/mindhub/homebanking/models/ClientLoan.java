package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private long id;
    private double Amount;
    private int Payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loans_id")
    private Loan loans;


    public ClientLoan() {
    }

    public ClientLoan(double amount, int payments, Client clientloan, Loan loans) {
        Amount = amount;
        Payments = payments;
        this.client = clientloan;
        this.loans = loans;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getPayments() {
        return Payments;
    }

    public void setPayments(int payments) {
        Payments = payments;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client clientloan) {
        this.client = clientloan;
    }
@JsonIgnore
    public Loan getLoan() {
        return loans;
    }

    public void setLoan(Loan loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "ClientLoan{" +
                "id=" + id +
                ", Amount=" + Amount +
                ", Payments=" + Payments +
                ", clientloan=" + client +
                ", loans=" + loans +
                '}';
    }
}

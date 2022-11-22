package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientLoanDTO {
    private long id;
    private long idloan;
    private double Amount;
    private int Payments;
    private String name;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.idloan = clientLoan.getLoan().getId();
        this.Amount = clientLoan.getAmount();
        this.Payments = clientLoan.getPayments();
        this.name = clientLoan.getLoan().getName();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdloan() {
        return idloan;
    }

    public void setIdloan(long idloan) {
        this.idloan = idloan;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

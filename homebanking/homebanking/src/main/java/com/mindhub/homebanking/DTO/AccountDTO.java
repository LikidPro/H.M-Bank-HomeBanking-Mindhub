package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.repositories.TransactionRepositort;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double ballance;

    private AccountType accountType;

    @Autowired
    private TransactionRepositort transactionRepositort;

    Set<TransactionDTO> transactions=new HashSet<>();

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.ballance = account.getBallance();
        this.transactions = account.getTransactions().stream().filter(transaction -> transaction.isTransactionState()==true).map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.accountType = account.getAccountType();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBallance() {
        return ballance;
    }

    public Set<TransactionDTO> getTransaction() {
        return transactions;
    }

    public AccountType getAccountType() {return accountType;}

    public TransactionRepositort getTransactionRepositort() {return transactionRepositort;}

    public Set<TransactionDTO> getTransactions() {return transactions;}
}

package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionService {
    public void transacctionSave(Transaction transaction);

    public TransactionDTO getTransaction(long id);

    public void disabletransaction(Account account);

    public void enabletransaction(Account account);

    public Set<Transaction> transactionFilter(LocalDateTime from, LocalDateTime to,Set<Transaction> transactions);




}

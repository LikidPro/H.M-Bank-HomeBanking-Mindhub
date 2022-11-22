package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepositort;
import com.mindhub.homebanking.repositories.TransactionRepositort;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
  private  TransactionRepositort transactionRepositort;

    @Override
    public void transacctionSave(Transaction transaction) {
        transactionRepositort.save(transaction);
    }

    @Override
    public TransactionDTO getTransaction(long id) {
        return transactionRepositort.findById(id).map(transaction -> new TransactionDTO(transaction)).orElse(null);
    }

    @Override
    public void disabletransaction(Account account) {
     account.getTransactions().stream().forEach(transaction -> {
         transaction.setTransactionState(false);
         transactionRepositort.save(transaction);
     });


    }

    @Override
    public void enabletransaction(Account account) {
        account.getTransactions().stream().forEach(transaction -> {
            transaction.setTransactionState(true);
            transactionRepositort.save(transaction);
        });

    }

    @Override
    public Set<Transaction> transactionFilter(LocalDateTime from, LocalDateTime to, Set<Transaction> transactions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        LocalDateTime localDateTime = LocalDateTime.parse(from,formatter);
//        System.out.println(LocalDateTime.parse(from,formatter));
        return transactions.stream().filter(transaction -> LocalDateTime.parse(transaction.getDate(),formatter).isAfter(from)  && LocalDateTime.parse(transaction.getDate(),formatter).isBefore(to)).collect(Collectors.toSet());

    }
}

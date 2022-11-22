package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepositort;
import com.mindhub.homebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    AccountRepositort accountRepositort;

    @Override
    public Set<AccountDTO> getAccounts() {
        return accountRepositort.findAll().stream().filter(account -> account.isAccountState()==true).map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }
    public Set<AccountDTO> getAccountsDisabled(){
        return accountRepositort.findAll().stream().filter(account -> account.isAccountState()==false).map(account -> new AccountDTO(account)).collect(Collectors.toSet());

    }

    @Override
    public void disabledAccount(long id) {
        Account account =  accountRepositort.findById(id).orElse(null);
        account.setAccountState(false);
        accountRepositort.save(account);
    }

    @Override
    public void enableAccount(long id) {
     Account account =  accountRepositort.findById(id).orElse(null);
     account.setAccountState(true);
     accountRepositort.save(account);
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepositort.findById(id).orElse(null);
    }

    @Override
    public AccountDTO getAccount(long id) {
            return accountRepositort.findById(id).map(account -> new AccountDTO(account)).orElse(null);

    }

    @Override
    public void saveAccount(Account account) {
         accountRepositort.save(account);
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepositort.findByNumber(number);
    }

}

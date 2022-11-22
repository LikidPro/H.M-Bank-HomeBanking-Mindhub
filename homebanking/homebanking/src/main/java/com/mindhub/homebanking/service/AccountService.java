package com.mindhub.homebanking.service;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.Set;

public interface AccountService {
    public Set<AccountDTO> getAccounts();

    public AccountDTO getAccount(long id);

    public void saveAccount(Account account);

    public Account getAccountByNumber(String number);

    public Set<AccountDTO> getAccountsDisabled();

    public void disabledAccount(long id);

    public void enableAccount(long id);

    public Account getAccountById(long id);



}

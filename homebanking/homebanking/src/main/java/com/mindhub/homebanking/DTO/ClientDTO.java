package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepositort;
import com.mindhub.homebanking.repositories.CardRepositort;
import com.mindhub.homebanking.repositories.ClientLoanRepositort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {

    private long id;
    private String firstName,lastName,email;

    @Autowired
    private AccountRepositort accountRepositort;
    Set<AccountDTO> accounts = new HashSet<>();

    @Autowired
    private ClientLoanRepositort clientLoanRepositort;

    Set<ClientLoanDTO> loans= new HashSet<>();

    @Autowired
     private CardRepositort cardRepositort;
    Set<CardDTO> cards= new HashSet<>();


    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts=client.getAccounts().stream().filter(account -> account.isAccountState()== true).map(item -> new AccountDTO(item)).collect(Collectors.toSet());
        this.loans= client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
        this.cards=client.getCards().stream().filter(card -> card.isCardState() == true).map(card -> new CardDTO(card)).collect(Collectors.toSet());

    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }


    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public AccountRepositort getAccountRepositort() {
        return accountRepositort;
    }

    public ClientLoanRepositort getClientLoanRepositort() {
        return clientLoanRepositort;
    }

    public CardRepositort getCardRepositort() {return cardRepositort;}

    public Set<CardDTO> getCards() {return cards;}

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}

package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mindhub.homebanking.utils.CardUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import static com.mindhub.homebanking.utils.CardUtils.*;


@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private long id;
    private String firstName, lastName, email, password ;
    private int activationCode;
    private boolean clientState;

    @OneToMany(mappedBy="client", fetch= FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans= new HashSet<>() {
    };

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<Card> cards= new HashSet<>();

    public Client() {
    }

   public Client(String firstName, String lastName, String email,String password) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.email = email;
       this.password= password;
       this.clientState= false;
       this.activationCode = CardUtils.getActivationCode();
    }


    public Set<Account> getAccounts() {
        return accounts;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {return cards;}

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isClientState() {return clientState;}

    public void setClientState(boolean clientState) {this.clientState = clientState;}

    @JsonIgnore
    public List<Loan> getLoans(){return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());}

    public int getActivationCode() {return activationCode;}

    public void setActivationCode(int activationCode) {this.activationCode = activationCode;}

    public void addaccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }
    public void addcard(Card card){
        card.setClient(this);
        cards.add(card);

    }


    @Override
   public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


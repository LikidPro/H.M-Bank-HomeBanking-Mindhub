//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.*;
//import com.mindhub.homebanking.service.LoanService;
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//import java.util.List;
//
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class RepositoriesTest {
//    @Autowired
//    LoansRepositort loansRepositort;
//
//    @Autowired
//    CardRepositort cardRepositort;
//
//    @Autowired
//    ClientRepositort clientRepositort;
//
//    @Autowired
//    TransactionRepositort transactionRepositort;
//
//    @Autowired
//    AccountRepositort accountRepositort;
//
//    @Test
//    public void existLoans(){
//        List<Loan> loans = loansRepositort.findAll();
//        assertThat(loans,is(not(empty())));
//
//    }
//
//    @Test
//    public void existPersonalLoan(){
//        List<Loan> loans = loansRepositort.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//
//    }
//    @Test
//    public void existCard(){
//        List<Card> cards = cardRepositort.findAll();
//        assertThat(cards,is(not(empty())));
//
//    }
//
//    @Test
//    public void existDebitCard(){
//        List<Card> cards = cardRepositort.findAll();
//        assertThat(cards, hasItem(hasProperty("cardType", is(CardType.DEBIT))));
//
//    }
//    @Test
//    public void existAccount(){
//        List<Account> accounts = accountRepositort.findAll();
//        assertThat(accounts,is(not(empty())));
//
//    }
//
//    @Test
//    public void existAccountByNumber(){
//        List<Account> accounts = accountRepositort.findAll();
//        assertThat(accounts, hasItem(hasProperty("number", is("VIN003"))));
//
//    }
//    @Test
//    public void existClient(){
//        List<Client> clients = clientRepositort.findAll();
//        assertThat(clients,is(not(empty())));
//
//    }
//
//    @Test
//    public void existClientByemail(){
//        List<Client> clients = clientRepositort.findAll();
//        assertThat(clients, hasItem(hasProperty("email", is("melba@mindhub.com"))));
//
//    }
//    @Test
//    public void existTransaction(){
//        List<Transaction> transactions = transactionRepositort.findAll();
//        assertThat(transactions,is(not(empty())));
//
//    }
//
//    @Test
//    public void existTransactionByDescription(){
//        List<Transaction> transactions = transactionRepositort.findAll();
//        assertThat(transactions, hasItem(hasProperty("description", is("venta art. 74"))));
//
//    }
//
//
//}

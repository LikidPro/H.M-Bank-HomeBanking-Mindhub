package com.mindhub.homebanking;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@SpringBootApplication
public class HomebankingApplication {
    @Autowired
//	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(CardRepositort cardRepositort, ClientLoanRepositort clientLoanRepositort, ClientRepositort clientRepositort, AccountRepositort accountRepositort, TransactionRepositort transactionRepositort, LoansRepositort loansRepositort){
		return args -> {
			//Date format
//			LocalDateTime mañana= LocalDateTime.now().plusDays(1);
//			DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//			String formatDateTime = now.format(format);
//			String mañana1= mañana.format(format);
//			LocalDateTime now= LocalDateTime.now();
//			System.out.println(now);
//			System.out.println(LocalDateTime.parse("05-9-2022 13:49:33",format));
//
//
//			Client client1= new Client("Melba","Morel","melba@mindhub.com", passwordEncoder.encode("123"));
//			Client client2= new Client("David","Garcia","davidgarcia430@hotmail.com",passwordEncoder.encode("1234"));
//			System.out.println(client1);
//
//			Account account1= new Account("VIN001", LocalDateTime.now(),5000);
//			Account account2= new Account("VIN002",LocalDateTime.now().plusDays(1),7500);
//			Account account3= new Account("VIN003",LocalDateTime.now().plusDays(2),8000);
//
//			Transaction transaction1= new Transaction(TransactionType.Credito,2600.00,"venta art. 24",formatDateTime);
//			Transaction transaction2= new Transaction(TransactionType.Credito,1600.50,"venta art.14",formatDateTime);
//			Transaction transaction3= new Transaction(TransactionType.Debito,-5200.00,"compra art. 4",formatDateTime);
//			Transaction transaction4= new Transaction(TransactionType.Credito,3700.00,"venta art. 34",formatDateTime);
//			Transaction transaction5= new Transaction(TransactionType.Credito,1300.50,"venta art. 74",formatDateTime);
//			Transaction transaction6= new Transaction(TransactionType.Debito,-1200.00,"compra art. 45",formatDateTime);
//			Transaction transaction7= new Transaction(TransactionType.Credito,2700.00,"venta art. 12",formatDateTime);
//			Transaction transaction8= new Transaction(TransactionType.Credito,1100.50,"venta art. 23",formatDateTime);
//			Transaction transaction9= new Transaction(TransactionType.Debito,-2200.00,"compra art. 6",formatDateTime);
//			Transaction transaction10= new Transaction(TransactionType.Credito,2600.00,"venta art. 24",formatDateTime);
//			Transaction transaction11= new Transaction(TransactionType.Credito,1600.50,"venta art. 14",formatDateTime);
//			Transaction transaction12= new Transaction(TransactionType.Debito,-5200.00,"compra art. 4",formatDateTime);
//			Transaction transaction13= new Transaction(TransactionType.Credito,3700.00,"venta art. 34",formatDateTime);
//			Transaction transaction14= new Transaction(TransactionType.Credito,1300.50,"venta art. 74",formatDateTime);
//			Transaction transaction15= new Transaction(TransactionType.Debito,-1200.00,"compra art. 45",formatDateTime);
//			Transaction transaction16= new Transaction(TransactionType.Credito,2700.00,"venta art. 12",formatDateTime);
//			Transaction transaction17= new Transaction(TransactionType.Credito,1100.50,"venta art. 23",formatDateTime);
//			Transaction transaction18= new Transaction(TransactionType.Debito,-2200.00,"compra art. 6",formatDateTime);
//
//			Loan loans1= new Loan("Hipotecario",500000.00,new ArrayList<>(List.of(12,24,36,48,60)));
//			Loan loans2= new Loan("Personal",100000.00,new ArrayList<>(List.of(6,12,24)));
//			Loan loans3= new Loan("Automotriz",300000.00,new ArrayList<>(List.of(6,12,24,36)));
//
//			ClientLoan clientLoan1= new ClientLoan(400000.00,60,client1,loans1);
//			ClientLoan clientLoan2= new ClientLoan(50000.00,12,client1,loans2);
//			ClientLoan clientLoan3= new ClientLoan(100000.00,24,client2,loans2);
//			ClientLoan clientLoan4= new ClientLoan(200000.00,36,client2,loans3);
//
//			Card card1= new Card(CardType.Debit,"1223-4512-3456-8565",542,client1.getFirstName()+ " " + client1.getLastName(), LocalDate.now(),LocalDate.now().plusYears(5),CardColor.Gold);
//			Card card2= new Card(CardType.Credit,"5522-5678-9234-4632",325,client1.getFirstName()+ " " + client1.getLastName(), LocalDate.now(),LocalDate.now().plusYears(5),CardColor.Titanium);
//			Card card3= new Card(CardType.Credit,"5567-5678-1539-5641",525,client2.getFirstName()+ " " + client2.getLastName(), LocalDate.now(),LocalDate.now().plusYears(5),CardColor.Silver);
//
//
//			clientRepositort.save(client1);
//			clientRepositort.save(client2);
//
//			client1.addaccount(account1);
//			client1.addaccount(account2);
//			client1.addaccount(account3);
//
//			accountRepositort.save(account1);
//			accountRepositort.save(account2);
//			accountRepositort.save(account3);
//
//
//			account1.addtransaction(transaction1);
//			account1.addtransaction(transaction2);
//			account1.addtransaction(transaction3);
//			account1.addtransaction(transaction10);
//			account1.addtransaction(transaction11);
//			account1.addtransaction(transaction12);
//			account1.addtransaction(transaction13);
//			account1.addtransaction(transaction14);
//			account1.addtransaction(transaction15);
//			account1.addtransaction(transaction16);
//			account1.addtransaction(transaction17);
//			account1.addtransaction(transaction18);
//			account2.addtransaction(transaction4);
//			account2.addtransaction(transaction5);
//			account2.addtransaction(transaction6);
//			account3.addtransaction(transaction7);
//			account3.addtransaction(transaction8);
//			account3.addtransaction(transaction9);
//
//			transactionRepositort.save(transaction1);
//			transactionRepositort.save(transaction2);
//			transactionRepositort.save(transaction3);
//			transactionRepositort.save(transaction4);
//			transactionRepositort.save(transaction5);
//			transactionRepositort.save(transaction6);
//			transactionRepositort.save(transaction7);
//			transactionRepositort.save(transaction8);
//			transactionRepositort.save(transaction9);
//			transactionRepositort.save(transaction10);
//			transactionRepositort.save(transaction12);
//			transactionRepositort.save(transaction13);
//			transactionRepositort.save(transaction14);
//			transactionRepositort.save(transaction15);
//			transactionRepositort.save(transaction16);
//			transactionRepositort.save(transaction17);
//			transactionRepositort.save(transaction18);
//			transactionRepositort.save(transaction11);
//
//			loansRepositort.save(loans1);
//			loansRepositort.save(loans2);
//			loansRepositort.save(loans3);
//
//			clientLoanRepositort.save(clientLoan1);
//			clientLoanRepositort.save(clientLoan2);
//			clientLoanRepositort.save(clientLoan3);
//			clientLoanRepositort.save(clientLoan4);
//
//			client1.addcard(card1);
//			client1.addcard(card2);
//			client2.addcard(card3);
//
//			cardRepositort.save(card1);
//			cardRepositort.save(card2);
//			cardRepositort.save(card3);
//
		};

	}










}

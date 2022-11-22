package com.mindhub.homebanking.controllers;
import com.lowagie.text.DocumentException;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.TransactionRepositort;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.PdfService;
import com.mindhub.homebanking.service.TransactionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://192.168.0.4:8080/")
public class TransactionControllers {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    TransactionRepositort transactionRepositort;

    private LocalDateTime localDateTime;

   private LocalDateTime now= LocalDateTime.now();
   private   LocalDateTime times= LocalDateTime.now();
   private   DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
   private   String formatDateTime = now.format(format);
   private   String mañana1= times.format(format);
   private String accountNumber;

    @PostMapping("transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable long id){
        return transactionService.getTransaction(id);
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transacction (
            @RequestParam Double amonunt,
            @RequestParam String description,
            @RequestParam String number,
            @RequestParam String numberDestiny,
            Authentication authentication
            ){
        Client current= clientService.getClientByEmail(authentication.getName());
        Account accountOrigin = accountService.getAccountByNumber(number);
        Account accountDestiny= accountService.getAccountByNumber(numberDestiny);
        if(amonunt.isNaN() || amonunt == 0){
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()){
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (number.isEmpty()){
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (numberDestiny.isEmpty()){
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (number.equals(numberDestiny)){
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (accountOrigin == null){
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (!current.getAccounts().contains(accountOrigin)) {
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (accountDestiny == null){
            return new  ResponseEntity<>("La cuenta ingresada no existe", HttpStatus.FORBIDDEN);
        }
        if (accountOrigin.getBallance() < amonunt){
            return new  ResponseEntity<>("El monto es mayor al que puedes transferir", HttpStatus.FORBIDDEN);
        }
        double ballanceOrigen = accountOrigin.getBallance() - amonunt;
        double ballanceDestiny = accountDestiny.getBallance() + amonunt;
        accountOrigin.setBallance(ballanceOrigen);
        accountDestiny.setBallance(ballanceDestiny);
        Transaction transaction1= new Transaction(TransactionType.Debito, -amonunt,description,formatDateTime,ballanceOrigen);
        Transaction transaction2= new Transaction(TransactionType.Credito,amonunt,description,formatDateTime,ballanceDestiny);
        transactionService.transacctionSave(transaction1);
       transactionService.transacctionSave(transaction2);
        accountOrigin.addtransaction(transaction1);
        accountDestiny.addtransaction(transaction2);
        accountService.saveAccount(accountOrigin);
        accountService.saveAccount(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping ("/pdf")
    public void pdf(HttpServletResponse response) throws DocumentException, IOException {

        Account account= accountService.getAccountByNumber(accountNumber);
        Set<Transaction> transactions = account.getTransactions();
        Set<Transaction> filtradas = transactionService.transactionFilter(times,now,transactions);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValues = "attachment; filename=pdf_"+ formatDateTime + ".pdf";
        response.setHeader(headerKey,headerValues);
        pdfService.export(account,filtradas,response);


    };


    @PatchMapping("/clients/current/accounts/transaction/pdf")
    public ResponseEntity<Object> setTimeString(
            @RequestParam String time,
            @RequestParam String number,
            Authentication authentication
              ) {

        Client client = clientService.getClientByEmail(authentication.getName());
        Account account = accountService.getAccountByNumber(number);

        if(client.getAccounts().contains(account)){
            accountNumber = number;
            if(time.equals("Ultima Semana")){
                times=LocalDateTime.now().minusDays(7);
            }
            if(time.equals("Ultimo mes")){
                times = LocalDateTime.now().minusWeeks(1);
            }
            if(time.equals("Ultimo semestre")){
                times = LocalDateTime.now().minusMonths(1);
            }
            if(time.equals("Ultimo año")){
                times = LocalDateTime.now().minusYears(1);
            }  return  new ResponseEntity<>( HttpStatus.OK);
        }
        return new ResponseEntity<>("Esta cuenta no te pertenece", HttpStatus.FORBIDDEN);


    }



}

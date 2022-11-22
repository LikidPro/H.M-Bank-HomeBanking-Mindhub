package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://192.168.0.4:8080/")
public class LoanControllers {
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
   private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public Set<LoanDTO> getLoansDTO(){
        return loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> askForLoan (
            @RequestBody LoanApplicationDTO loanApplicationDTO,
            Authentication authentication
    ){
        LocalDateTime now= LocalDateTime.now();
        LocalDateTime mañana= LocalDateTime.now().plusDays(1);
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDateTime = now.format(format);
        String mañana1= mañana.format(format);
        Loan loan = loanService.getLoanById(loanApplicationDTO.getIdLoan());
        Account cuentaDestino = accountService.getAccountByNumber(loanApplicationDTO.getNumberAccountsDestiny());
        Client current = clientService.getClientByEmail(authentication.getName());
        Double addedAmount = null;

        if (loanApplicationDTO.getIdLoan() == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getAmount().isNaN() || loanApplicationDTO.getAmount() == 0 ){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getPayments() == null || loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (loanApplicationDTO.getNumberAccountsDestiny().isEmpty()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (loan== null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(loanApplicationDTO.getAmount() > loan.getMaxAmount()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("",HttpStatus.FORBIDDEN);
        }
        if(accountService.getAccountByNumber(loanApplicationDTO.getNumberAccountsDestiny()) == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(!current.getAccounts().contains(cuentaDestino)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (loan.getName().equals("Hipotecario")){
            if(loanApplicationDTO.getPayments() == 12) {
                addedAmount = loanApplicationDTO.getAmount() * 1.20;
            }
            if(loanApplicationDTO.getPayments() == 24) {
                addedAmount = loanApplicationDTO.getAmount() * 1.22;
            }
            if(loanApplicationDTO.getPayments() == 36) {
                addedAmount = loanApplicationDTO.getAmount() * 1.24;
            }
            if(loanApplicationDTO.getPayments() == 48) {
                addedAmount = loanApplicationDTO.getAmount() * 1.28;
            }
            if(loanApplicationDTO.getPayments() == 60) {
                addedAmount = loanApplicationDTO.getAmount() * 1.30;
            }

        }
        if (loan.getName().equals("Automotriz")){
            if(loanApplicationDTO.getPayments() == 6){
                addedAmount = loanApplicationDTO.getAmount() * 1.15;
            }
            if(loanApplicationDTO.getPayments() == 12){
                addedAmount = loanApplicationDTO.getAmount() * 1.17;
            }
            if(loanApplicationDTO.getPayments() == 24){
                addedAmount = loanApplicationDTO.getAmount() * 1.20;
            }
            if(loanApplicationDTO.getPayments() == 36){
                addedAmount = loanApplicationDTO.getAmount() * 1.22;
            }

        }
        if (loan.getName().equals("Personal")){
            if ((loanApplicationDTO.getPayments() == 6)){
                addedAmount = loanApplicationDTO.getAmount() * 1.10;
            }
            if ((loanApplicationDTO.getPayments() == 12)){
                addedAmount = loanApplicationDTO.getAmount() * 1.12;
            }
            if ((loanApplicationDTO.getPayments() == 24)){
                addedAmount = loanApplicationDTO.getAmount() * 1.14;
            }
            if ((loanApplicationDTO.getPayments() == 36)){
                addedAmount = loanApplicationDTO.getAmount() * 1.18;
            }

        }
        if(current.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan().getName().equals(loan.getName())).toArray().length == 1){
            return new  ResponseEntity<>("Ya posees un prestamo de este tipo",HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(addedAmount,loanApplicationDTO.getPayments(),current,loan);
        Transaction transaction =new Transaction(TransactionType.Credito,loanApplicationDTO.getAmount(),loan.getName() + " loan approved",formatDateTime,cuentaDestino.getBallance() + loanApplicationDTO.getAmount());
        cuentaDestino.addtransaction(transaction);
        transactionService.transacctionSave(transaction);
        clientLoanService.clientLoanSave(clientLoan);
        cuentaDestino.setBallance(cuentaDestino.getBallance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(cuentaDestino);

        return new  ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/loans/admin")
    public ResponseEntity<Object> createdLoans(
            @RequestParam String name, @RequestParam Double maxAmount, @RequestParam List<Integer> payments
    ){
        Loan loan = new Loan(name,maxAmount,payments);
        loanService.saveLoan(loan);
        return new  ResponseEntity<>("Prestamo creado con exito", HttpStatus.CREATED);

    }
    @PatchMapping("/loans/admin")
    public ResponseEntity<Object> deleteLoans(
           @RequestParam long id
    ){

        loanService.deleteLoan(id);
        return new  ResponseEntity<>("Prestamo eliminado con exito", HttpStatus.CREATED);

    }


}

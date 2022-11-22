package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
@CrossOrigin("http://192.168.0.4:8080/")
public class AccountControllers {
    //Math random method
    private long getRandomNumber(long min,long max) {
        return (long) ((Math.random() * (max - min))+min);
    }


    private LocalDateTime localDateTime;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;




    @GetMapping("/accounts")
    public Set<AccountDTO> getAccounts(){
            return accountService.getAccounts();
        };
    @GetMapping("/accounts/disabled")
    public Set<AccountDTO> getAccountsDisabled(){
        return accountService.getAccountsDisabled();
    };
    @GetMapping("accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable long id, Authentication authentication){
        if (clientService.getClientByEmail(authentication.getName()).getAccounts().contains(accountService.getAccount(id))){
        return new  ResponseEntity<>(accountService.getAccount(id), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Esta cuenta no te pertenece", HttpStatus.FORBIDDEN);

    }
    @PostMapping ("clients/current/accounts")
    public ResponseEntity<Object> registerAccounts(
            @RequestParam AccountType accountType,
            Authentication authentication){

        Client client = clientService.getClientByEmail(authentication.getName());
      if (client.getAccounts().toArray().length >= 3){
            return new ResponseEntity<>("maximum accounts reached", HttpStatus.FORBIDDEN);
        }
      if ( authentication == null){
          return new ResponseEntity<>("does not have authorization", HttpStatus.UNAUTHORIZED);
      }
        Account curret= new Account("VIN-" + getRandomNumber(10000000,99999999), LocalDateTime.now(),0,accountType);
        client.addaccount(curret);
        accountService.saveAccount(curret);
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping ("clients/current/accounts")
    public Set<AccountDTO> getAccountsCurrent(Authentication authentication){
        Client current = clientService.getClientByEmail(authentication.getName());
      return    current.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    };

    @PatchMapping("clients/current/accounts/{id}/disabled")
    public ResponseEntity<Object> disabledAccount(@PathVariable long id){
        Account account = accountService.getAccountById(id);
        transactionService.disabletransaction(account);
        accountService.disabledAccount(id);
        return new ResponseEntity<>("Cuenta deshabilitada",HttpStatus.ACCEPTED );
    }
    @PatchMapping("clients/current/accounts/{id}/enable")
    public ResponseEntity<Object> enableAccount(@PathVariable long id){
        Account account = accountService.getAccountById(id);
        transactionService.enabletransaction(account);
        accountService.enableAccount(id);
        return new ResponseEntity<>("Cuenta habilitada",HttpStatus.ACCEPTED );
    }
    };



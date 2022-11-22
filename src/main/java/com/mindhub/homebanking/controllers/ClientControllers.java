package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepositort;
import com.mindhub.homebanking.repositories.ClientRepositort;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://192.168.0.4:8080/")
public class ClientControllers {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    private long getRandomNumber(long min,long max) {
        return (long) ((Math.random() * (max - min))+min);
    }

  @GetMapping("/clients")
    public List<ClientDTO> getClients(){
      return clientService.getClientsDTO();
  }
  @GetMapping("/clients/disabled")
  public List<ClientDTO> getClientsDisabled(){ return clientService.getClientsDTODisabled();}
  @GetMapping("clients/{id}")
    public ClientDTO getClient(@PathVariable long id){
      return clientService.getClientDTO(id);
  }
  @PostMapping ("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,@RequestParam String email, @RequestParam String password){
      if (firstName.isEmpty() ){
          return new ResponseEntity<>("Missing firstName", HttpStatus.FORBIDDEN);

      }
      if ( lastName.isEmpty()){
          return new ResponseEntity<>("Missing lastName", HttpStatus.FORBIDDEN);

      }
      if (email.isEmpty() ){
          return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);

      }
      if (password.isEmpty()){
          return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);

      }
      if (clientService.getClientByEmail(email) != null){
          return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
      }
      Client client= new Client(firstName,lastName,email,passwordEncoder.encode(password));
      Account account= new Account("VIN-" + getRandomNumber(10000000,99999999), LocalDateTime.now(),0, AccountType.CORRIENTE);
      client.addaccount(account);
      clientService.saveClient(client);
      accountService.saveAccount(account);
      return new ResponseEntity<>(client.getActivationCode() ,HttpStatus.CREATED);
  }
  @GetMapping("/client/current")
    public ClientDTO getClientAutenticado(Authentication authentication){
      return new ClientDTO(clientService.getClientByEmail(authentication.getName()));
  }
  @PatchMapping("/client/current")
  public ResponseEntity<Object> setClient(
          @RequestParam String firstName,
          @RequestParam String lastName,
          @RequestParam String password,
          Authentication authentication){
        Client current = clientService.getClientByEmail(authentication.getName());
        if(current != null){
            current.setPassword(passwordEncoder.encode(password));
            current.setFirstName(firstName);
            current.setLastName(lastName);
            clientService.saveClient(current);
            return new ResponseEntity<>("Informacion actualizada",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("No tienes autorizacion",HttpStatus.FORBIDDEN);
  }
  @PatchMapping ("/clients/current/password")
    public ResponseEntity<Object> changePassword(Authentication authentication,@RequestParam String password){
        Client current= clientService.getClientByEmail(authentication.getName());
        current.setPassword(passwordEncoder.encode(password));
        clientService.saveClient(current);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping ("/clients/{id}/disabled")
    public ResponseEntity<Object> disabledClient(@PathVariable long id){
        clientService.disabledClient(id);
        return new  ResponseEntity<>("Cliente deshabilitado", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/clients/{id}/enable")
    public ResponseEntity<Object> enableClient(@PathVariable long id){
        clientService.enableClient(id);
        return new  ResponseEntity<>("Cliente habilitado", HttpStatus.ACCEPTED);
    }

    @PostMapping("/client/send/email")
    public ResponseEntity<Object> mandarEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String textMessage
    ){
        emailService.sendEmailTool(textMessage,to,subject);
        return new ResponseEntity<>("mensaje enviado" , HttpStatus.ACCEPTED);

    }
    @PatchMapping("/client/validation")
    public ResponseEntity<Object> validationClient(
            @RequestParam String email,
            @RequestParam int activationCode
    ){
      Client client =  clientService.getClientByEmail(email);
      if(client.getActivationCode() == activationCode){
          client.setClientState(true);
          clientService.saveClient(client);
          return new ResponseEntity<>("Su email ah sido confirmado ya puede iniciar seccion",HttpStatus.ACCEPTED);
      }else return new ResponseEntity<>("El codigo no es valido",HttpStatus.NOT_ACCEPTABLE);
    }

};
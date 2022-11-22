package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import static com.mindhub.homebanking.utils.CardUtils.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://192.168.0.4:8080/")
public class CardControllers {

    private LocalDate localDate;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;

    @PostMapping ("/clients/current/cards")
    public ResponseEntity<Object> registerCards(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication){


        Client current= clientService.getClientByEmail(authentication.getName());
        if (current.getCards().stream().filter(card -> card.getCardType() == cardType && card.isCardState()==true).collect(Collectors.toSet()).toArray().length >= 3){
            return new ResponseEntity<>("Maximo de tarjetas de este tipo creadas", HttpStatus.FORBIDDEN);
        }
        if (current.getCards().stream().filter(card -> card.getCardType() == cardType).filter(card -> card.getCardColor() == cardColor).toArray().length == 1){
            return new ResponseEntity<>("Solo puedes tener una tarjeta del mismo color", HttpStatus.FORBIDDEN);
        }
        Card card= new Card(cardType, getCardNumber(), getCardCvv(),current.getFirstName()+ " " + current.getLastName(),localDate.now(),localDate.now().plusYears(5),cardColor);
        current.addcard(card);
        cardService.cardSave(card);
        clientService.saveClient(current);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteCards(
            @RequestParam long id,
            Authentication authentication
    ){
        Client current = clientService.getClientByEmail(authentication.getName());
        Card card = cardService.getCardById(id);

        if (id == 0 ){
            return new ResponseEntity<>("Esta tarjeta no existe",HttpStatus.FORBIDDEN);
        }
        if(current == null){
            return  new ResponseEntity<>("No tienes autorizacion", HttpStatus.FORBIDDEN);
        }
        if(!current.getCards().contains(card)){
           return new ResponseEntity<>("Esta tarjeta no te pertenece", HttpStatus.FORBIDDEN);
        }
        cardService.deleteCard(card);
        return  new ResponseEntity<>("Tarjeta borrada con exito", HttpStatus.ACCEPTED);
    }
    @PatchMapping("cards/{id}/disabled")
    public ResponseEntity<Object> disabledCard(@PathVariable long id){
        cardService.disabledCard(id);
        return new ResponseEntity<>("Tarjeta deshabilitada", HttpStatus.ACCEPTED);
    }
    @PatchMapping("cards/{id}/enable")
    public ResponseEntity<Object> enableCard(@PathVariable long id){
        cardService.enableCard(id);
        return new ResponseEntity<>("Tarjeta deshabilitada", HttpStatus.ACCEPTED);
    }
    @GetMapping("/cards/disabled")
    public Set<CardDTO> cardsDisabled(){
        return cardService.cardsDisabled();
    }


}

//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.utils.CardUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//@SpringBootTest
//public class cardUtilTest {
//
//    @Test
//    public void cardNumberIsCreated(){
//
//        String cardNumber = CardUtils.getCardNumber();
//        assertThat(cardNumber,is(not(emptyOrNullString())));
//
//    }
//    @Test
//    public void cardNumberIsString(){
//
//        String cardNumber = CardUtils.getCardNumber();
//        assertThat(cardNumber,isA(String.class));
//
//    }
//    @Test
//    public void cardCvvIsInt(){
//
//        int cardNumber = CardUtils.getCardCvv();
//        assertThat(cardNumber,isA(int.class));
//
//    }
//    @Test
//
//    public void cardCvvIsCreated(){
//
//        int cardNumber = CardUtils.getCardCvv();
//        assertThat(cardNumber,notNullValue(int.class));
//
//    }
//}

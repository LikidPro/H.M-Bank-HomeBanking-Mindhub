package com.mindhub.homebanking.utils;

public final class CardUtils {

    public static String getCardNumber() {
        return (int) (Math.random() * (10000 - 1000) + 1000)
                + "-" + (int) (Math.random() * (10000 - 1000) + 1000)
                + "-" + (int) (Math.random() * (10000 - 1000) + 1000)
                + "-" + (int) (Math.random() * (10000 - 1000) + 1000);
    }
    public static int getCardCvv() {
        return (int)((Math.random()* (999 - 100))+ 100);
    }

    public static int getActivationCode() {
        return (int) ((Math.random()* (99999 - 10000))+ 10000);
    }
}

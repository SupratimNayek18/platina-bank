package com.platinabank.cards.util;

import java.util.Random;

public class IdGenerator {

    public static long generateCardId(){
        Random random = new Random();
        return 100000000000L + random.nextLong(900000000000L);
    }

    public static long generateCardNumber(){
        Random random = new Random();
        return 1000000000000000L + random.nextLong(9000000000000000L);
    }

}

package com.platinabank.accounts.util;

import java.util.Random;

public class IdGenerator {

    private IdGenerator(){}

    public static int generateCustomerId(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public static long generateAccountId(){
        Random random = new Random();
        return 100000000000L + random.nextLong(900000000000L);
    }

}

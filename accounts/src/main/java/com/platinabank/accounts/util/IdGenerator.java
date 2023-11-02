package com.platinabank.accounts.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IdGenerator {

    public int generateCustomerId(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public long generateAccountId(){
        Random random = new Random();
        return 100000000000L + random.nextLong(900000000000L);
    }

}

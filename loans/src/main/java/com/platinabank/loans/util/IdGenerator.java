package com.platinabank.loans.util;

import java.util.Random;

public class IdGenerator {

    public static long generateLoanId(){
        Random random = new Random();
        return 100000000000L + random.nextLong(900000000000L);
    }

    public static long generateLoanNumber(){
        Random random = new Random();
        return 10000000000000L + random.nextLong(90000000000000L);
    }

}

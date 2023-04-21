package com.pm490.PM490.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class BankResponseServiceUtil {

    private boolean response;

    public boolean bankResponse(double amount, Long id){
        Random random = new Random();
        response = random.nextBoolean();
        return response;
    }

}

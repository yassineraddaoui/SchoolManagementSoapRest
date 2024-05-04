package com.jee.project.demo.config;

import java.util.Random;

public class OTP {
    public static String generateOTP(){
        Random random = new Random();
        int randomNumber = random.nextInt(999999);
        String output = Integer.toString(randomNumber);

        while (output.length() < 6) {
            output = "0" + output;
        }
        return output;
    }
}

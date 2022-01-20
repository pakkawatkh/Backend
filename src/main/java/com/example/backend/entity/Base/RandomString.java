package com.example.backend.entity.Base;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomString {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    String alphabetA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String alphabet123 = "1234567890";

    public String strImage() {
        Integer length = 5;
        return this.Random(alphabet,length);

    }

    public String number() {
        Integer length =5;
        return this.Random(alphabet123,length);
    }
//    public String dateChange() {
//        Integer length =5;
//        return this.Random(alphabet123,length);
//    }

    public String Random(String str, Integer length) {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < length; i++) {

            int index = random.nextInt(str.length());

            char randomChar = str.charAt(index);

            sb.append(randomChar);
        }

        return sb.toString();
    }




}

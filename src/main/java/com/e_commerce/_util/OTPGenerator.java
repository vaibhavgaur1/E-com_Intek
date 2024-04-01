package com.e_commerce._util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.util.Random;

@UtilityClass
public class OTPGenerator {

   @SneakyThrows
   public Long generateOtp() {
              try{
                  int[] digits = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
                  Random random = new Random();
                  StringBuilder sb = new StringBuilder();
                  for (int i = 0; i < 6; i++) {
                      int digit = digits[random.nextInt(digits.length)];
                      sb.append(digit);
                  }
                  String otp = sb.toString();
                  System.out.println(" successfull : "+   otp);
                  return Long.valueOf(otp);
              }catch (Exception e){
                  throw new Exception("otp can not generate");
              }
   }
}

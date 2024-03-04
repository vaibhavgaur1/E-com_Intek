package com.e_commerce.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class OtpDto {

    private String cardNumber;
    private Long otp;
    private String cardType;
}

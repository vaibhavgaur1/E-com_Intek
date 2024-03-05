package com.e_commerce.Dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class LoginDto {

    private String cardType;
    private String cardNumber;
//    private Date dob;
    private String password;
}
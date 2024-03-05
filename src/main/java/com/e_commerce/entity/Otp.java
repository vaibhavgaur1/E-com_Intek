package com.e_commerce.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Otp {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private Long otp;
    private boolean isVerified;

}

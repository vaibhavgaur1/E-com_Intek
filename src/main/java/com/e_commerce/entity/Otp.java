package com.e_commerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder@Entity
public class Otp {

    @Id@GeneratedValue
    private Long id;
    private String email;
    private Long otp;
    private boolean isVerified;
}

package com.e_commerce.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {

    private String cardNumber;
    private String userPassword;
//    private String cardType;

}

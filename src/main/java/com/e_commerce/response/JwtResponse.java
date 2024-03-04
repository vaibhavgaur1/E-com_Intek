package com.e_commerce.response;

import com.e_commerce.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

    private User user;
    private String jwtToken;
}

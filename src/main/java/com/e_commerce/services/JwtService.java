package com.e_commerce.services;

import com.e_commerce._configuration.UserDetailService;
import com.e_commerce._util.JwtUtil;
import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.User;
import com.e_commerce.request.JwtRequest;
import com.e_commerce.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service@RequiredArgsConstructor
public class JwtService {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {

        System.out.println("JWT_REQUEST: "+jwtRequest);

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getCardNumber(), jwtRequest.getUserPassword()
            ));
        }catch (DisabledException e){
            throw new Exception("user is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("bad credentials from user");
        }
        UserDetails userDetails = userDetailService.userDetailsService()
                .loadUserByUsername(jwtRequest.getCardNumber());

        String generatedToken = jwtUtil.generateToken(userDetails);

        User user = userDao.findByLiquorCardNumberOrGroceryCardNumber(jwtRequest.getCardNumber(),jwtRequest.getCardNumber()).get(0);
        return JwtResponse.builder()
                .jwtToken(generatedToken)
                .user(user)
                .build();
    }
}

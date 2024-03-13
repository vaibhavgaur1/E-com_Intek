package com.e_commerce.services.impl;

import com.e_commerce._configuration.UserDetailService;
import com.e_commerce._util.JwtUtil;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.User;
import com.e_commerce.request.JwtRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.JwtResponse;
import com.e_commerce.services.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    public ApiResponse<JwtResponse> createJwtToken(JwtRequest jwtRequest, String cardType) throws Exception {

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
                .loadUserByUsername(jwtRequest.getCardNumber()); //+"-"+cardType

        String generatedToken = jwtUtil.generateToken(userDetails);

        User user= (User) userDetails;
        System.out.println(user);

//        User user = userDao.findByLiquorCardNumberOrGroceryCardNumber(jwtRequest.getCardNumber(),jwtRequest.getCardNumber()).get(0);
        JwtResponse build = JwtResponse.builder()
                .jwtToken(generatedToken)
                .user(user)
                .build();
        return ResponseUtils.createSuccessResponse(build, new TypeReference<JwtResponse>() {});
    }
}

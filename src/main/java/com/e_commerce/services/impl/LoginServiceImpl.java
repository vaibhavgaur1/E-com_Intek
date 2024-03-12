package com.e_commerce.services.impl;

import com.e_commerce._configuration.UserDetailService;
import com.e_commerce._util.JwtUtil;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.request.LoginRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.LoginResponce;
import com.e_commerce.services.LoginService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;


public class LoginServiceImpl implements LoginService {

    private  JwtUtil jwtUtil;
    private  AuthenticationManager authenticationManager;
    private  UserDetailService userDetailService;

    @Override
    public ApiResponse<LoginResponce> getLoginData(LoginRequest loginrequest) throws Exception {
        LoginResponce loginResponce = new LoginResponce();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginrequest.getPassword(), loginrequest.getPassword()
            ));
        }catch (DisabledException e){
            throw new Exception("user is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("bad credentials from user");
        }
        UserDetails userDetails = userDetailService.userDetailsService()
                .loadUserByUsername(loginrequest.getCardNo()); //+"-"+cardType

        String generatedToken = jwtUtil.generateToken(userDetails);

        loginResponce.setToken(generatedToken);
        loginResponce.setMessage("Success");



        return ResponseUtils.createSuccessResponse(loginResponce, new TypeReference<LoginResponce>() {
        });
    }
}

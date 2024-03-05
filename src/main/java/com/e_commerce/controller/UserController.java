package com.e_commerce.controller;

import com.e_commerce.Dto.OtpDto;
import com.e_commerce.Dto.RegisterDto;
import com.e_commerce.entity.User;
import com.e_commerce.request.JwtRequest;
import com.e_commerce.response.JwtResponse;
import com.e_commerce.services.JwtService;
import com.e_commerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registerNewUser")
    public User registerNewUser(@RequestBody RegisterDto registerDto) throws Exception {
        return userService.registerNewUser(registerDto);
    }

    @PostMapping("/otpVerify")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpDto otpDto) {
        String message= userService.verifyOtp(otpDto)? "otp verified": "otp can not be verified";
        return ResponseEntity.ok(message);
    }

    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createJwtToken(
            @RequestBody JwtRequest jwtRequest,
            @RequestHeader("cardType") String cardType
//            @RequestParam(value = "loginType", defaultValue = "by-pass") String loginType
    ) throws Exception {
        System.out.println("controller");
        return ResponseEntity.ok(jwtService.createJwtToken(jwtRequest, cardType ));
    }

}

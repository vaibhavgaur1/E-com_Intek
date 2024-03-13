package com.e_commerce.controller;

import com.e_commerce.Dto.AddressDto;
import com.e_commerce.Dto.OtpDto;
import com.e_commerce.Dto.RegisterDto;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.entity.User;
import com.e_commerce.request.JwtRequest;
import com.e_commerce.request.LoginRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.JwtResponse;
import com.e_commerce.response.LoginResponce;
import com.e_commerce.services.JwtService;
import com.e_commerce.services.UserService;
import com.e_commerce.services.impl.JwtServiceImpl;
import com.e_commerce.services.LoginService;
import com.e_commerce.services.impl.UserServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/loginAuth")
@Slf4j
public class UserController {

    @Autowired
    public LoginService loginService;
    @Autowired
    public UserService userService;
    @Autowired
    public JwtService jwtService;


    @PostMapping("/registerNewUser")
    public ResponseEntity<ApiResponse<User>> registerNewUser(@RequestBody RegisterDto registerDto) throws Exception {
        return ResponseEntity.ok(userService.registerNewUser(registerDto));
    }

    @GetMapping("/generate-otp")
    public ResponseEntity<ApiResponse<String>> generateOtp(@RequestParam("email") String email) throws MessagingException {
        return ResponseEntity.ok(userService.generateOtp(email));
    }

    @PostMapping("/otpVerify")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody OtpDto otpDto) {
        String message= userService.verifyOtp(otpDto)? "otp verified": "otp can not be verified";
        return ResponseEntity.ok(ResponseUtils.createSuccessResponse(message, new TypeReference<String>() {}));
    }



    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<JwtResponse>> createJwtToken(
            @RequestBody JwtRequest jwtRequest,
            @RequestHeader("cardType") String cardType
//            @RequestParam(value = "loginType", defaultValue = "by-pass") String loginType
    ) throws Exception {
        System.out.println("controller");
        return ResponseEntity.ok(jwtService.createJwtToken(jwtRequest, cardType ));
    }
    @PostMapping("/addAddress")
    public ResponseEntity<ApiResponse<AddressDto>> addAddress(@RequestBody AddressDto addressDto) {
//        String message= userService.addAddress(addressDto)? "Address Added": "Something Went Wrong";
        return ResponseEntity.ok(userService.addAddress(addressDto));
    }

    @GetMapping("/getAddress/{userId}")

    public  ResponseEntity<ApiResponse<List<AddressDto>>>  getAddress(@PathVariable Integer userId ) {
//        String message= userService.getUserAddress(addressDto)? "Address Added": "Something Went Wrong";
        return ResponseEntity.ok(userService.getUserAddress(userId));
    }
//    @PostMapping("/authenticate1")
//    public ResponseEntity<ApiResponse<LoginResponce>> getLoginData(@RequestBody LoginRequest loginrequest) throws Exception {
//        return new ResponseEntity<>(loginService.getLoginData(loginrequest), HttpStatus.OK);
//    }


}

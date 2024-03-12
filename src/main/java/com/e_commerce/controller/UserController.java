package com.e_commerce.controller;

import com.e_commerce.Dto.AddressDto;
import com.e_commerce.Dto.OtpDto;
import com.e_commerce.Dto.RegisterDto;
import com.e_commerce.entity.User;
import com.e_commerce.request.JwtRequest;
import com.e_commerce.request.LoginRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.JwtResponse;
import com.e_commerce.response.LoginResponce;
import com.e_commerce.services.JwtService;
import com.e_commerce.services.LoginService;
import com.e_commerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private LoginService loginService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/registerNewUser")
    public User registerNewUser(@RequestBody RegisterDto registerDto) throws Exception {
        return userService.registerNewUser(registerDto);
    }

    @GetMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestParam("email") String email){
        return ResponseEntity.ok(userService.generateOtp(email));
    }

    @PostMapping("/otpVerify")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpDto otpDto) {
        String message= userService.verifyOtp(otpDto)? "otp verified": "otp can not be verified";
        return ResponseEntity.ok(message);
    }



    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createJwtToken(
            @RequestBody JwtRequest jwtRequest,
            @RequestHeader("cardType") String cardType
//            @RequestParam(value = "loginType", defaultValue = "by-pass") String loginType
    ) throws Exception {
        System.out.println("controller");
        return ResponseEntity.ok(jwtService.createJwtToken(jwtRequest, cardType ));
    }
    @PostMapping("/addAddress")
    public ResponseEntity<AddressDto> addAddress(@RequestBody AddressDto addressDto) {
//        String message= userService.addAddress(addressDto)? "Address Added": "Something Went Wrong";
        return ResponseEntity.ok(userService.addAddress(addressDto));
    }

    @GetMapping("/getAddress/{userId}")
    public ResponseEntity<List<AddressDto>>  getAddress(@PathVariable Integer userId ) {
//        String message= userService.getUserAddress(addressDto)? "Address Added": "Something Went Wrong";
        return ResponseEntity.ok(userService.getUserAddress(userId));
    }
    @PostMapping("/authenticate1")
    public ResponseEntity<ApiResponse<LoginResponce>> getLoginData(@RequestBody LoginRequest loginrequest) throws Exception {
        return new ResponseEntity<>(loginService.getLoginData(loginrequest), HttpStatus.OK);
    }


}

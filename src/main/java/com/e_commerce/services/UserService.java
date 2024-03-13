package com.e_commerce.services;

import com.e_commerce.Dto.AddressDto;
import com.e_commerce.Dto.OtpDto;
import com.e_commerce.Dto.RegisterDto;
import com.e_commerce.entity.User;
import com.e_commerce.response.ApiResponse;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {

    ApiResponse<User> registerNewUser(RegisterDto registerDto) throws Exception;

    void initRoleAndUser();

    boolean verifyOtp(OtpDto otpDto);

    ApiResponse<String> generateOtp(String email) throws MessagingException;

    ApiResponse<AddressDto> addAddress(AddressDto addressDto);

    ApiResponse<List<AddressDto>> getUserAddress(Integer userId);
}

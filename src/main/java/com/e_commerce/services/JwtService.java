package com.e_commerce.services;

import com.e_commerce.request.JwtRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.JwtResponse;

public interface JwtService {

    ApiResponse<JwtResponse> createJwtToken(JwtRequest jwtRequest, String cardType) throws Exception;
}

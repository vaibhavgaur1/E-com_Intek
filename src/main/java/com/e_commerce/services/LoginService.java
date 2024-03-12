package com.e_commerce.services;

import com.e_commerce.request.LoginRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.LoginResponce;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    ApiResponse<LoginResponce> getLoginData(LoginRequest loginrequest) throws Exception;
}

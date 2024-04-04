package com.e_commerce.exception;

import com.e_commerce.response.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionHandler1 {


    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e){

        return ApiResponse.builder()
                .response("exception "+e.getMessage())
                .isProduction(true)
                .key(null)
                .message("")
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponse handleException1(BadCredentialsException e){

        return ApiResponse.builder()
                .response("exception "+e.getMessage())
                .isProduction(true)
                .key(null)
                .message("")
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse handleException2(RuntimeException e){

        return ApiResponse.builder()
                .response("exception "+e.getMessage())
                .isProduction(true)
                .key(null)
                .message("")
                .build();
    }

    @ExceptionHandler(IOException.class)
    public ApiResponse handleException3(IOException e){

        return ApiResponse.builder()
                .response("exception "+e.getMessage())
                .isProduction(true)
                .key(null)
                .message("")
                .build();
    }

//    @ExceptionHandler(IOException.class)
//    public ApiResponse handleException4(IOException e){
//
//        return ApiResponse.builder()
//                .response("exception "+e.getMessage())
//                .isProduction(true)
//                .key(null)
//                .message("")
//                .build();
//    }


}

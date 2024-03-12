package com.e_commerce.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse<T> {
    T response;
    private Integer status;
    private String message;
    private String salt;
    private boolean isProduction;
    private String key;
}

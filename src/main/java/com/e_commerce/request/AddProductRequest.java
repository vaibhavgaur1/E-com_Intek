package com.e_commerce.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddProductRequest {

    private String productName;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;

    private Long categoryId;
    private String uploadId;

}

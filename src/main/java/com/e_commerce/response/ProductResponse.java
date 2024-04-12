package com.e_commerce.response;

import com.e_commerce.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Integer productId;
    private String productName;
    private boolean isWishList;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;
    private String imageUrl;
    private String image;
    private String uploadId;
    private Category category;
}

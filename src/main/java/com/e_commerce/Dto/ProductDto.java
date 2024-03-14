package com.e_commerce.Dto;

import com.e_commerce.entity.Category;
import com.e_commerce.entity.ImageModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer productId;
    private String productName;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;
    private Set<ImageModel> productImages;
    private byte[] image;



    private Long categoryId;
}

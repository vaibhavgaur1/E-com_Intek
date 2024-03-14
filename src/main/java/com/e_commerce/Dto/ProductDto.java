package com.e_commerce.Dto;


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
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Integer productId;

    private String productName;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;

    private Long categoryId;
    private String uploadId;
}

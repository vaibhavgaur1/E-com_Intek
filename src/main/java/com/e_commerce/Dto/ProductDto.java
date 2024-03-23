package com.e_commerce.Dto;

import com.e_commerce.entity.Category;
import com.e_commerce.entity.ImageModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDto {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Integer productId;

    private String productName;
    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;
    private String uploadId;
//    private Set<ImageModel> productImages;
//    private byte[] image;

    private Long categoryId;

}

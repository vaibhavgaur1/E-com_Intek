package com.e_commerce.Dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter@Setter@Builder@AllArgsConstructor@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private String description;
    private Long totalProducts;
    private String uploadId;
    private byte[] image;
    private String imageUrl;
}

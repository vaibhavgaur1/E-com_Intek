package com.e_commerce.entity;

import com.e_commerce.Dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer productId;
    private String productName;
    @Column(length = 2000)

    private String productDescription;
    private Double productDiscountedPrice;
    private Double productActualPrice;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_images",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id")
            }
    )
    private Set<ImageModel> productImages;

    @ManyToOne
    @JsonManagedReference
    private Category category;

    public ProductDto getDto() {
        return ProductDto.builder()
                .productId(productId)
                .productName(productName)
                .productDescription(productDescription)
                .productDiscountedPrice(productDiscountedPrice)
                .productActualPrice(productActualPrice)
                .productImages(productImages)
                .categoryId(category.getId())
                .build();
    }
}

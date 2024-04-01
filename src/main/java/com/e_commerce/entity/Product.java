package com.e_commerce.entity;

import com.e_commerce.Dto.FileUpload;
import com.e_commerce.Dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String imageUrl;
//
//    @Column(unique = true)
//    private String uploadDocId;

//    @OneToOne(fetch = FetchType.EAGER)
//    private FileUpload fileUpload;

//    @Lob
    private byte[] image;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "product_images",
//            joinColumns = {
//                    @JoinColumn(name = "product_id")
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "image_id")
//            }
//    )
//    private Set<ImageModel> productImages;
    private String uploadId;
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "product_images",
//            joinColumns = {
//                    @JoinColumn(name = "product_id")
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "image_id")
//            }
//    )
//    private Set<ImageModel> productImages;

    @ManyToOne
    @JsonManagedReference
    @JsonIgnore
    private Category category;

    public ProductDto generateDto() {
        return ProductDto.builder()
                .productId(productId)
                .productName(productName)
                .productDescription(productDescription)
                .productDiscountedPrice(productDiscountedPrice)
                .productActualPrice(productActualPrice)

//                .image(image)

//                .productImages(productImages)
                .uploadId(getUploadId())

                .categoryId(category.getId())
                .build();
    }
}

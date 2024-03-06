package com.e_commerce.entity;

import com.e_commerce.Dto.CategoryDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, unique = true)
    private String name;
    @Column(length = 500)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonBackReference
    private List<Product> products;

    public CategoryDto getDto() {
        return CategoryDto.builder()
                .name(name)
                .id(id)
                .description(description)
                .build();
    }
}

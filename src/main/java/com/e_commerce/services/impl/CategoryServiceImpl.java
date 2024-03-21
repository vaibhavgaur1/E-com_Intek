package com.e_commerce.services.impl;

import com.e_commerce.Dto.CategoryDto;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.CategoryRepository;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.entity.Category;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.CategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductDao productDao;

    @SneakyThrows
    @Override
    public ApiResponse<CategoryDto> addCategory(CategoryDto categoryDto) {

        Boolean isExists= categoryRepository.existsByName(categoryDto.getName());

        if(isExists)
            throw new Exception("Category with name "+categoryDto.getName()+" already exists!");
        Category category= Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();

        CategoryDto categoryDto1 = categoryRepository.save(category).getDto();
        categoryDto1.setTotalProducts(productDao.countByColumnName(category.getId()));
        try{
            return ResponseUtils.createSuccessResponse(categoryDto1, new TypeReference<CategoryDto>() {});
        }catch (Exception e){
            throw new Exception("something went wrong");
        }

    }

    @Override
    public ApiResponse<List<CategoryDto>> getAllCategories() {

        List<Category> all = categoryRepository.findAll();
        List<CategoryDto> list = new ArrayList<>();
        all.forEach(category -> {
            list.add(
                    CategoryDto.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .description(category.getDescription())
                            .totalProducts(productDao.countByColumnName(category.getId()))
                            .build()
            );

        });
        return ResponseUtils.createSuccessResponse(list, new TypeReference<List<CategoryDto>>() {});
    }

    @Override
    public ApiResponse<List<String>> getAllCategoriesNamesOnly() {
        return ResponseUtils.createSuccessResponse(categoryRepository.getAllCategoriesNamesOnly(), new TypeReference<List<String>>() {});
    }

    @SneakyThrows
    @Override
    public ApiResponse<CategoryDto> updateCategory(CategoryDto categoryDto) {

        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new Exception("category doesn't exists"));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return ResponseUtils.createSuccessResponse(categoryRepository.save(category).getDto(), new TypeReference<CategoryDto>() {});
    }
}

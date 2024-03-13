package com.e_commerce.services;

import com.e_commerce.Dto.CategoryDto;
import com.e_commerce.response.ApiResponse;

import java.util.List;

public interface CategoryService {


    ApiResponse<CategoryDto> addCategory(CategoryDto categoryDto);

    ApiResponse<List<CategoryDto>> getAllCategories();

    ApiResponse<List<String>> getAllCategoriesNamesOnly();

    ApiResponse<CategoryDto> updateCategory(CategoryDto categoryDto);
}

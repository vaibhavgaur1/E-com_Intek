package com.e_commerce.services;

import com.e_commerce.Dto.CategoryDto;

import java.util.List;

public interface CategoryService {


    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    List<String> getAllCategoriesNamesOnly();

    CategoryDto updateCategory(CategoryDto categoryDto);
}

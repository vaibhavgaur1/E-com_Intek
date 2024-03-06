package com.e_commerce.services.impl;

import com.e_commerce.Dto.CategoryDto;
import com.e_commerce.dao.CategoryRepository;
import com.e_commerce.entity.Category;
import com.e_commerce.services.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @SneakyThrows
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Boolean isExists= categoryRepository.existsByName(categoryDto.getName());

        if(isExists)
            throw new Exception("Category with name "+categoryDto.getName()+" already exists!");
        Category category= Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
        try{
            return categoryRepository.save(category).getDto();
        }catch (Exception e){
            throw new Exception("something went wrong");
        }

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getDto)
                .toList();
    }

    @Override
    public List<String> getAllCategoriesNamesOnly() {
        return categoryRepository.getAllCategoriesNamesOnly();
    }

    @SneakyThrows
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new Exception("category doesn't exists"));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category).getDto();
    }
}

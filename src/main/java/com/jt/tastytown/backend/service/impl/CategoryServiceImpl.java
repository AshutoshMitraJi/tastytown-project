package com.jt.tastytown.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jt.tastytown.backend.dto.CategoryRequestDTO;
import com.jt.tastytown.backend.entity.Category;
import com.jt.tastytown.backend.exception.CategoryNotFoundException;
import com.jt.tastytown.backend.repository.CategoryRepository;
import com.jt.tastytown.backend.service.ICategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository categoryRepository;

    public Category saveCategory(CategoryRequestDTO requestDTO) {
        var category = Category.builder().categoryName(requestDTO.getCategoryName()).build();
        return categoryRepository.save(category);
    }

    public List<Category> findAllCategories() {
        var categories = categoryRepository.findAll();
        return categories;
    }

    public Category getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found with provided ID: " + categoryId));
    }

    public Category updateCategory(String categoryId, CategoryRequestDTO requestDTO) {
        var existingCategory = getCategoryById(categoryId);
        existingCategory.setCategoryName(requestDTO.getCategoryName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(String categoryId) {
        getCategoryById(categoryId);
        categoryRepository.deleteById(categoryId);
    }
}

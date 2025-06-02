package com.jt.tastytown.backend.service;

import org.springframework.stereotype.Service;

import com.jt.tastytown.backend.dto.CategoryRequestDTO;
import com.jt.tastytown.backend.entity.Category;
import com.jt.tastytown.backend.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    
    public Category saveCategory(CategoryRequestDTO requestDTO) {
        var category = Category.builder().categoryName(requestDTO.getCategoryName()).build();
        return categoryRepository.save(category);
    }
}
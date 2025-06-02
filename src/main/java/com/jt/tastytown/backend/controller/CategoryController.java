package com.jt.tastytown.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jt.tastytown.backend.dto.CategoryRequestDTO;
import com.jt.tastytown.backend.entity.Category;
import com.jt.tastytown.backend.service.CategoryService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping    
    public Category createCategory(@RequestBody CategoryRequestDTO requestDTO) {
        return categoryService.saveCategory(requestDTO);
    }
}

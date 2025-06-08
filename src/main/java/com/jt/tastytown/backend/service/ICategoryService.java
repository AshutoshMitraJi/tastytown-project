package com.jt.tastytown.backend.service;

import java.util.List;

import com.jt.tastytown.backend.dto.CategoryRequestDTO;
import com.jt.tastytown.backend.entity.Category;

public interface ICategoryService {
    /**
     * <h3> It creates a Category object by Category name </h3>
     */
    Category saveCategory(CategoryRequestDTO requestDTO);
    List<Category> findAllCategories();
    Category getCategoryById(String categoryId);
    Category updateCategory(String categoryId, CategoryRequestDTO requestDTO);
    void deleteCategory(String categoryId);
}

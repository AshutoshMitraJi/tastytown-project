package com.jt.tastytown.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.tastytown.backend.dto.CategoryRequestDTO;
import com.jt.tastytown.backend.entity.Category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;

import com.jt.tastytown.backend.service.ICategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name="Category API", description = "This controller manages CRUD operations for Category Entity")
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    @ApiResponse(responseCode="201", description="Category Created")
    @Operation(summary="Create a new Category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequestDTO requestDTO) {
        // return categoryService.saveCategory(requestDTO);

        // var savedCategory = categoryService.saveCategory(requestDTO);
        // return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

        return new ResponseEntity<>(categoryService.saveCategory(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping  
    @ApiResponse(description="All Categories fetched Successfully!")
    @Operation(summary="Extract all Categories")
    public ResponseEntity<List<Category>> extractCategory(){
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @GetMapping("/{categoryId}")
    @ApiResponse(description="Category fetched Successfully by ID!")
    @Operation(summary="Extract a Category")
    public ResponseEntity<Category> getCategoryById(@PathVariable String categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    
    @PutMapping("/{categoryId}")
    @ApiResponse(description="Category updated Successfully by ID!")
    @Operation(summary="Update a Category")
    public ResponseEntity<Category> updateCategory(@PathVariable String categoryId, @RequestBody CategoryRequestDTO requestDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, requestDTO));
    }

    @DeleteMapping("/{categoryId}")
    @ApiResponse(responseCode="204", description="Category deleted Successfully!")
    @Operation(summary="Delete a Category")
    // @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}

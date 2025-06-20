package com.jt.tastytown.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.tastytown.backend.entity.Food;

public interface FoodRepository extends JpaRepository<Food, String>{
    //custom query method
    Page<Food> findByCategory_CategoryIdAndFoodNameContainingIgnoreCase(String categoryId, String foodName, Pageable pageable);
    Page<Food> findByFoodNameContainingIgnoreCase(String foodName, Pageable pageable);
    Page<Food> findByCategory_CategoryId(String categoryId, Pageable pageable);
}

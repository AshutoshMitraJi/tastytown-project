package com.jt.tastytown.backend.dto;

public record CartItemResponse(
    String foodId,
    int quantity,
    String foodName,
    String foodCategoryName,
    double foodPrice) {
    
}

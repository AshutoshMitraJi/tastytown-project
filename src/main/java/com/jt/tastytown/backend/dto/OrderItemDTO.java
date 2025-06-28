package com.jt.tastytown.backend.dto;

public record OrderItemDTO(
    String foodName,
    double foodPrice,
    int quantity) {
    
}

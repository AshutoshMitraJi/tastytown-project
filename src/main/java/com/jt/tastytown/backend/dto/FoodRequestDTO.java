package com.jt.tastytown.backend.dto;


public record FoodRequestDTO(
    String foodName,
    String foodDescription,

    String foodImage,

    double foodPrice,
    String categoryId
) {

}
package com.jt.tastytown.backend.mapper;

import java.util.List;

import com.jt.tastytown.backend.dto.CartItemResponse;
import com.jt.tastytown.backend.dto.CartResponseDTO;
import com.jt.tastytown.backend.entity.Cart;

public class CartMapper {

    private CartMapper() {}
     public static CartResponseDTO convertToCartResponseDTO(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(
            item -> new CartItemResponse(item.getFood().getFoodId(),
                        item.getQuantity(),
                        item.getFood().getFoodName(),
                        item.getFood().getCategory().getCategoryName(),
                        item.getFood().getFoodPrice())
        ).toList();
        return new CartResponseDTO(items);
    }
}
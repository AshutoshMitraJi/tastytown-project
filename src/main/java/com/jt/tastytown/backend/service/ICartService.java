package com.jt.tastytown.backend.service;

import com.jt.tastytown.backend.dto.CartItemRequestDTO;
import com.jt.tastytown.backend.dto.CartResponseDTO;

public interface ICartService {
    CartResponseDTO addItemToCart(String userId, CartItemRequestDTO cartItemRequestDTO);
    CartResponseDTO getCartByUserId(String userId);
    CartResponseDTO updateItemQuantity(String userId, CartItemRequestDTO cartItemRequestDTO);
    CartResponseDTO removeItemFromCart(String userId, String foodId);
    void clearCartItems(String userId);
}

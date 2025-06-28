package com.jt.tastytown.backend.helper;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.jt.tastytown.backend.entity.Cart;
import com.jt.tastytown.backend.entity.User;
import com.jt.tastytown.backend.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartServiceHelper {
    private final CartRepository cartRepository;
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user).orElseThrow(() -> new NoSuchElementException("Cart Not Found for the User"));
    }
}

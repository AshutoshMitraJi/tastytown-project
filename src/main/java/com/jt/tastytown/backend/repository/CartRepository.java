package com.jt.tastytown.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.tastytown.backend.entity.Cart;
import com.jt.tastytown.backend.entity.User;


public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUser(User user);
    void deleteByUser(User user);
}

package com.jt.tastytown.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jt.tastytown.backend.entity.Order;
import com.jt.tastytown.backend.entity.User;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUser(User user, Sort sort);
    Optional<Order> findByOrderCode(String orderCode);
}
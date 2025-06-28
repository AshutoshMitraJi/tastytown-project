package com.jt.tastytown.backend.service;

import java.util.List;

import com.jt.tastytown.backend.constants.OrderStatus;
import com.jt.tastytown.backend.dto.BillingInfoDTO;
import com.jt.tastytown.backend.dto.OrderDTO;

public interface IOrderService {
    OrderDTO placeOrder(BillingInfoDTO dto, String userId);
    List<OrderDTO> getOrdersByUser(String userId);
    List<OrderDTO> getAllOrders();
    OrderDTO updateOrderStatus(String orderCode, OrderStatus status);
}
package com.jt.tastytown.backend.mapper;

import java.util.List;

import com.jt.tastytown.backend.dto.OrderDTO;
import com.jt.tastytown.backend.dto.OrderItemDTO;
import com.jt.tastytown.backend.entity.Order;

public class OrderMapper {
    private OrderMapper() {}

    public static OrderDTO convertToOrderDTO(Order order) {
        List<OrderItemDTO> items = order.getOrderItems().stream().map(orderItem -> new OrderItemDTO(orderItem.getFoodName(), orderItem.getFoodPrice(), orderItem.getQuantity())).toList();
        return new OrderDTO(
                            order.getOrderCode(),
                            items,
                            order.getTotalAmount(),
                            order.getOrderStatus().toString(),
                            order.getOrderDateTime(),
                            order.getContactInfo(),
                            order.getAddressInfo());
    }
}
package com.jt.tastytown.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jt.tastytown.backend.constants.OrderStatus;
import com.jt.tastytown.backend.dto.BillingInfoDTO;
import com.jt.tastytown.backend.dto.OrderDTO;
import com.jt.tastytown.backend.entity.Cart;
import com.jt.tastytown.backend.entity.CartItem;
import com.jt.tastytown.backend.entity.Order;
import com.jt.tastytown.backend.entity.OrderItem;
import com.jt.tastytown.backend.entity.User;
import com.jt.tastytown.backend.helper.CartServiceHelper;
import com.jt.tastytown.backend.helper.UserServiceHelper;
import com.jt.tastytown.backend.mapper.OrderMapper;
import com.jt.tastytown.backend.repository.OrderRepository;
import com.jt.tastytown.backend.service.IOrderService;
import com.jt.tastytown.backend.utils.OrderCodeGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final UserServiceHelper userServiceHelper;
    private final CartServiceHelper cartServiceHelper;
    private final OrderRepository orderRepository;

    @Value("${order.delivery.fee}")
    private double deliveryFee;

    @Value("${order.tax.rate}")
    private double taxRate;

    @Override
    public OrderDTO placeOrder(BillingInfoDTO dto, String userId) {
        var user = userServiceHelper.getUserById(userId);
        var cart = cartServiceHelper.getCartByUser(user);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is Empty");
        }
        var order = createOrderFromCart(cart, dto, user);
        return OrderMapper.convertToOrderDTO(order);
    }

    @Override
    public List<OrderDTO> getOrdersByUser(String userId) {
        var user = userServiceHelper.getUserById(userId);
        return orderRepository.findAllByUser(user, Sort.by(Sort.Direction.DESC, "orderDateTime")).stream().map(OrderMapper :: convertToOrderDTO).toList();
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDateTime")).stream().map(OrderMapper :: convertToOrderDTO).toList();
    }

    @Override
    public OrderDTO updateOrderStatus(String orderCode, OrderStatus status) {
        var order = orderRepository.findByOrderCode(orderCode).orElseThrow(() -> new NoSuchElementException("Order Not Found by Order Code: " + orderCode));
        order.setOrderStatus(status);
        var savedOrder = orderRepository.save(order);
        return OrderMapper.convertToOrderDTO(savedOrder);
    }

    private Order createOrderFromCart(Cart cart, BillingInfoDTO billingInfo, User user) {
        var order = new Order();
        order.setUser(user);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderCode(OrderCodeGenerator.generateOrderCode());
        order.setOrderStatus(OrderStatus.FOOD_PREPARING);
        order.setContactInfo(formatContactInfo(billingInfo));
        order.setAddressInfo(formatAddressInfo(billingInfo));

        double subTotal = 0;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodPrice(cartItem.getFood().getFoodPrice());
            orderItem.setFoodName(cartItem.getFood().getFoodName());
            orderItem.setQuantity(cartItem.getQuantity());

            subTotal += cartItem.getFood().getFoodPrice() * cartItem.getQuantity();
            order.getOrderItems().add(orderItem);
        }

        double totalAmount = subTotal + deliveryFee + (subTotal * taxRate);
        order.setTotalAmount(totalAmount);

        var savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    private String formatContactInfo(BillingInfoDTO billingInfo) {
        return String.join(", ", 
                            billingInfo.fullName(),
                            billingInfo.email(),
                            billingInfo.phoneNumber());
        //ashu, a@gmail.com, 4512784596
    }

    private String formatAddressInfo(BillingInfoDTO billingInfo) {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(billingInfo.address());
        joiner.add(billingInfo.city());
        joiner.add(billingInfo.state());
        joiner.add(billingInfo.zip());
        
        return joiner.toString();
    }
}

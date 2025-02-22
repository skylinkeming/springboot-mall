package com.stevelin.springbootmall.service;

import com.stevelin.springbootmall.dto.CreateOrderRequest;
import com.stevelin.springbootmall.dto.OrderQueryParams;
import com.stevelin.springbootmall.model.Order;

import java.util.List;

public interface OrderService {
    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}

package com.stevelin.springbootmall.service;

import com.stevelin.springbootmall.dto.CreateOrderRequest;
import com.stevelin.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}

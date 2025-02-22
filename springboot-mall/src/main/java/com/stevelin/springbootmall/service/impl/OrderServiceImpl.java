package com.stevelin.springbootmall.service.impl;

import com.stevelin.springbootmall.dao.OrderDao;
import com.stevelin.springbootmall.dao.ProductDao;
import com.stevelin.springbootmall.dao.UserDao;
import com.stevelin.springbootmall.dto.BuyItem;
import com.stevelin.springbootmall.dto.CreateOrderRequest;
import com.stevelin.springbootmall.dto.OrderQueryParams;
import com.stevelin.springbootmall.model.Order;
import com.stevelin.springbootmall.model.OrderItem;
import com.stevelin.springbootmall.model.Product;
import com.stevelin.springbootmall.model.User;
import com.stevelin.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {

        return orderDao.countOrder(orderQueryParams);
    }


    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orders = orderDao.getOrders(orderQueryParams);

        for (Order order : orders) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }

        return orders;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);


        return order;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // check if user exists
        User user = userDao.getUserById(userId);
        if (user == null) {
            logger.warn("userId: {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            // check if the stock of the product is enough
            if (product == null) {
                logger.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            if (product.getStock() < buyItem.getQuantity()) {
                logger.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量 {}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // update stock
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            // calculate total amount
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount += amount;

            // convert BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // create order
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}

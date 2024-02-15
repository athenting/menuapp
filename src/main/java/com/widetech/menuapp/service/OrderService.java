package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dto.responses.OrderResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
@Service
public interface OrderService {

    List<Order> getAllOrders(int limit);
//    create new order not supported, order can only be created via com.widetech.menuapp.service.CartService.checkout
//    public Order createOrder(Order order);

    public OrderResult getOrder(Integer id);

    public OrderResult updateOrder(Double price, Integer id);

    public void deleteOrder(Integer id);

    public OrderResult proceedToPay(Integer id);
}

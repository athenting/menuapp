package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
@Service
public interface OrderService {

    List<Order> getAllOrders(int limit);

    public Order createOrder(Order order);

    public Optional<Order> getOrder(Integer id);

    public Order updateOrder(Double price, Integer id);

    public void deleteOrder(Integer id);

    public Order proceedToPay(Integer id);
}

package com.widetech.menuapp.service.impl;

import cn.hutool.core.math.Money;
import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dao.repository.OrderRepository;
import com.widetech.menuapp.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        Page<Order> page = orderRepository.findAll(pageRequest);
        return page.getContent();
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrder(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order updateOrder(Double price, Integer id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setTotalPrice(new Money(price));
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order proceedToPay(Integer id) {
        //todo: proceed to payment functions
        return null;
    }

}

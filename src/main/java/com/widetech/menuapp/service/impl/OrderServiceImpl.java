package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dao.repository.OrderRepository;
import com.widetech.menuapp.dto.responses.OrderResult;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    //    create new order not supported
//    @Override
//    @Transactional
//    public Order createOrder(Order order) {
//        return orderRepository.save(order);
//    }
    @Override
    public List<Order> getAllOrders(int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        Page<Order> page = orderRepository.findAll(pageRequest);
        return page.getContent();
    }


    @Override
    public OrderResult getOrder(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }
        return OrderResult.builder().
                orderDate(order.get().getOrderDate().toString()).
                orderId(String.valueOf(order.get().getId())).
                customerId(String.valueOf(order.get().getCustomer().getId())).
                totalPrice(order.get().getTotalPrice().toString()).
                build();
    }

    @Override
    @Transactional
    public OrderResult updateOrder(Double price, Integer id) {
        Order order = orderRepository.findById(id)
                .map(o -> {
                    o.setTotalPrice(new BigDecimal(price));
                    return orderRepository.save(o);
                })
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found"));

        return OrderResult.builder()
                .orderId(String.valueOf(order.getId()))
                .totalPrice(order.getTotalPrice().toString())
                .customerId(String.valueOf(order.getCustomer().getId()))
                .orderDate(order.getOrderDate().toString())
                .build();
    }

    @Override
    @Transactional
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderResult proceedToPay(Integer id) {
        //todo: proceed to payment functions
        return null;
    }
}

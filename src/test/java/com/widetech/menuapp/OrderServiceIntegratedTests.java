package com.widetech.menuapp;

import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dao.repository.OrderRepository;
import com.widetech.menuapp.dto.responses.OrderResult;
import com.widetech.menuapp.service.impl.OrderServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: athen
 * Date: 2/7/2024
 * Description:
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class OrderServiceIntegratedTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Rollback(value = false)
    public void testGetAllOrders() {
        // Call getAllOrders with limit 10
        List<Order> orders = orderService.getAllOrders(10);
        // Validate the return value
        assertFalse(orders.isEmpty());
    }

    @Test
    public void testGetOrder() {
        // Assume there is an order with id 202 in the database
        OrderResult orderResult = orderService.getOrder(202);
        assertNotNull(orderResult);
        assertEquals("202", orderResult.getId());
    }

    @Test
    public void testUpdateOrder() {
        // Assume there is an order with id 202 in the database
        OrderResult orderResult = orderService.updateOrder(20.0, 202);
        assertNotNull(orderResult);
        assertEquals("202", orderResult.getId());
        assertEquals("20", orderResult.getTotalPrice());
    }

    @Test
    public void testDeleteOrder() {
        // Assume you have an order with id 202 in the database
        Order saveOrder = new Order();
        entityManager.persist(saveOrder);

        // Delete existing order
        // Here, assuming the order was successfully created with an id
        orderService.deleteOrder(saveOrder.getId());

        // Validate the order is deleted
        Order deletedOrder = entityManager.find(Order.class, saveOrder.getId());
        assertNull(deletedOrder);
    }

    @Test
    public void testProceedToPay() {
        // Test the proceedToPay method
        OrderResult orderResult = orderService.proceedToPay(202);
        // Validate the return value
        assertNotNull(orderResult);
    }
}
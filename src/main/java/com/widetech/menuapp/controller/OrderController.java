package com.widetech.menuapp.controller;

import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dto.responses.OrderResult;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: athen
 * Date: 2/5/2024
 * Description:
 */
@RestController
@RequestMapping("/api/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public List<Order> getAllOrders(Integer limit) {
        return orderService.getAllOrders(limit);
    }

//    @PostMapping("/order")
//    public Order newOrder(Order newOrder) {
//        return orderService.createOrder(newOrder);
//    }

    @GetMapping("/order/{orderId}")
    public RestResponse<OrderResult> queryOrderById(@PathVariable Integer orderId) {
        OrderResult order = orderService.getOrder(orderId);
        return new RestResponse<>(order);
    }

    @PatchMapping("/order/{orderId}")
    public OrderResult updatePrice(@Parameter Double price, @PathVariable Integer orderId) {

        return orderService.updateOrder(price, orderId);
    }

    @DeleteMapping("/order/{orderId}")
    public void delete(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
    }

    @PostMapping("/order/{orderId}/pay")
    public RestResponse<String> proceedToPay(@PathVariable Integer orderId) {
        orderService.proceedToPay(orderId);

        //todo: payment business logic to be done, add order status

        return RestResponse.success("Payment success.");
    }
}
package com.widetech.menuapp.controller;

import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Author: athen
 * Date: 2/5/2024
 * Description:
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    public List<Order> getAllOrders(Integer limit) {
        return orderService.getAllOrders(limit);
    }

    @PostMapping("/order")
    public Order newOrder(Order newOrder) {
        return orderService.createOrder(newOrder);
    }

    @GetMapping("/order/{id}")
    public RestResponse<Order> queryOrderById(@PathVariable Integer id) {
        Optional<Order> orderOptional = orderService.getOrder(id);
        return orderOptional.map(RestResponse::new).orElseGet(RestResponse::notFound);
    }

    @PatchMapping("/order/{id}")
    public Order updatePrice(@Parameter Double price, @PathVariable Integer id) {

        return orderService.updateOrder(price, id);
    }

    @DeleteMapping("/order/{id}")
    public void delete(@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/order/{id}/pay")
    public RestResponse proceedToPay(@PathVariable Integer id) {
        orderService.proceedToPay(id);

        //todo: payment business logic to be done

        return RestResponse.success();
    }
}
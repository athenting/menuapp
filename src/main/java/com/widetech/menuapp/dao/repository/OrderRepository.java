package com.widetech.menuapp.dao.repository;

import com.widetech.menuapp.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}

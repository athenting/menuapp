package com.widetech.menuapp.dao.repository;

import com.widetech.menuapp.dao.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}

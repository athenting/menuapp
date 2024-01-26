package com.widetech.menuapp.dao.repository;

import com.widetech.menuapp.dao.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart getCartByCustomerId(Integer customer_id);

}

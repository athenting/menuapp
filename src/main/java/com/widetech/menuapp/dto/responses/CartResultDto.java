package com.widetech.menuapp.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: athen
 * Date: 2/14/2024
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResultDto {

    @Schema(description = "cart_id")
    private String cartId;

    @Schema(description = "customer_id")
    private String customerId;

    @Schema(description = "cart_items")
    private List<CartItemResultDto> cartItems = new ArrayList<>();
}

package com.widetech.menuapp.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Author: athen
 * Date: 2/15/2024
 * Description:
 */
@Data
@Builder
public class OrderResult {

    @Schema(description = "order_id")
    private String orderId;

    @Schema(description = "total_price")
    private String totalPrice;

    @Schema(description = "order_date")
    private String orderDate;

    @Schema(description = "customer id")
    private String customerId;

    @Schema(description = "cart item list")
    List<CartItemResultDto> cartItems;
}

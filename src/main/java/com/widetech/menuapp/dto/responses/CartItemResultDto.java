package com.widetech.menuapp.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: athen
 * Date: 2/14/2024
 * Description:
 */
@Data
@NoArgsConstructor
public class CartItemResultDto {

    @Schema(description = "cart_item_id")
    private String cartItemId;

    @Schema(description = "menu_item_id")
    private String menuItemId;

    @Schema(description = "name")
    private String name;

    @Schema(description = "quantity")
    private String quantity;

    @Schema(description = "cart_id")
    private String cart;

    @Schema(description = "price")
    private String price;
}

package com.widetech.menuapp.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * Author: athen
 * Date: 2/15/2024
 * Description:
 */
@Data
@NoArgsConstructor
public class CartItemDto {

    @Schema(description = "menu_item_id", required = true)
    @NotBlank
    private String menuItemId;

    @Schema(description = "quantity", required = true)
    @NotBlank
    @Length(min = 1, max = 100)
    private String quantity;

}
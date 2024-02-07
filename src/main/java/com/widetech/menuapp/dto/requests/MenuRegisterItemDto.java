package com.widetech.menuapp.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Author: athen
 * Date: 2/6/2024
 * Description:
 */
@Data
public class MenuRegisterItemDto {

    @Schema(description = "menu item name", required = true)
    @NotBlank
    private String name;

    @Schema(description = "menu item price", required = true)
    @NotBlank
    private String price;

    @Schema(description = "menu id the item belongs to", required = true)
    @NotBlank
    private String menuId;

}

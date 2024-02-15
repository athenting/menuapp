package com.widetech.menuapp.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Author: athen
 * Date: 2/13/2024
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
public class MenuItemResultDto {

    @Schema(description = "menu item id")
    private String itemId;

    @Schema(description = "menu item name")
    private String name;

    @Schema(description = "menu item description")
    private String description;

    @Schema(description = "menu item price")
    private String price;

    @Schema(description = "menu id the item belongs to")
    private String menuId;

    public MenuItemResultDto() {
    }
}

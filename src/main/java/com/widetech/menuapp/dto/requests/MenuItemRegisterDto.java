package com.widetech.menuapp.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Author: athen
 * Date: 2/6/2024
 * Description:
 */
@Data
public class MenuItemRegisterDto {

    @Schema(description = "menu item name", required = true)
    @NotBlank
    @Length(min = 1, max = 40)
    private String name;

    @Schema(description = "menu item description", required = true)
    @Length(min = 1, max = 100)
    private String description;

    @Schema(description = "menu item price", required = true)
    @NotBlank
    @Length(min = 1, max = 10)
    private String price;

    @Schema(description = "menu id the item belongs to", required = true)
    @NotBlank
    @Length(min = 1, max = 10)
    private String menuId;

}

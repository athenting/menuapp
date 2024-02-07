package com.widetech.menuapp.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Author: athen
 * Date: 1/24/2024
 * Description:
 */
@Data
public class MenuRegisterDto {

    @Schema(description = "menu name", required = true)
    @NotBlank
    private String name;

    @Schema(description = "menu description", required = true)
    private String description;

}
package com.widetech.menuapp.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Author: athen
 * Date: 1/24/2024
 * Description:
 */
@Data
public class MenuRegisterDto {

    @Schema(description = "menu name", required = true)
    @NotBlank
    @Length(min = 1, max = 40)
    private String name;

    @Schema(description = "menu description", required = true)
    @Length(min = 1, max = 100)
    private String description;

}
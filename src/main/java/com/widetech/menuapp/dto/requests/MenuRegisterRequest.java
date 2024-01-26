package com.widetech.menuapp.dto.requests;

import com.widetech.menuapp.dao.entity.MenuItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * Author: athen
 * Date: 1/24/2024
 * Description:
 */
@Data
public class MenuRegisterRequest {

    @Schema(description = "menu name", required = true)
    @NotBlank
    private String name;

    @Schema(description = "menu description", required = true)
    private String description;

    @NotEmpty
    @Valid
    @Schema(description = "items or dishes that gonna be added to the menu", required = true)
    private List<MenuItem> itemList;
}
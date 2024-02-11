package com.widetech.menuapp.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Author: athen
 * Date: 2/07/2024
 * Description:
 */
@Builder
@Data
public class AdminRegisterResultDto {
    @Schema(description = "admin id")
    private Integer id;

    @Schema(description = "admin name")
    private String name;

    @Schema(description = "email")
    private String email;

    @Schema(description = "user token")
    private String status;
}

package com.widetech.menuapp.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Author: athen
 * Date: 2/1/2024
 * Description:
 */
@Data
@Builder
public class AdminLoginResultDto {

    @Schema(description = "admin id")
    private Integer id;

    @Schema(description = "email")
    private String email;

    @Schema(description = "user token")
    private String status;

}

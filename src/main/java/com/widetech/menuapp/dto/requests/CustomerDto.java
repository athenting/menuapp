package com.widetech.menuapp.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * Author: athen
 * Date: 1/31/2024
 * Description:
 */
@Getter
@AllArgsConstructor
public class CustomerDto {

    @Schema(description = "name", required = false)
    @Length(min = 1, max = 40)
    private String name;

    @Schema(description = "phoneNumber", required = true, example = "18888888888")
    @NotBlank
    @Pattern(regexp = "^1[3|4|5|6|7|8|9][0-9]{9}$", message = "incorrect format")
    private String phoneNumber;

}

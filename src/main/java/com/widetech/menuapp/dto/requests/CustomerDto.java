package com.widetech.menuapp.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: athen
 * Date: 1/31/2024
 * Description:
 */
@Getter
@AllArgsConstructor
public class CustomerDto {

    private String name;

    @NotBlank
    private String phoneNumber;

}

package com.widetech.menuapp.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Author: athen
 * Date: 2/1/2024
 * Description:
 */
@Data
public class AdminLoginDto {

    /**
     * email
     */
    @Schema(description = "email", required = true)
    @NotBlank(message = "email cannot be blank！")
    @Email(message = "incorrect email format！")
    @Length(min = 6,max = 40)
    private String email;

    /**
     * password
     */
    @Schema(description = "password", required = true, example = "123456")
    @Length(min = 6,max = 20)
    @NotBlank(message = "password cannot be blank！")
    private String password;

}

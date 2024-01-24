package com.widetech.menuapp.dto.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Author: athen
 * Date: 1/24/2024
 * Description:
 */
@Data
public class ApiErrorResponse {

    private String errorCode;
    private String errorDescription;
    private HttpStatus status;

    public ApiErrorResponse(String errorCode, String errorDescription, HttpStatus status) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.status = status;
    }
}
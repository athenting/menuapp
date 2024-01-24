package com.widetech.menuapp.exception;

/**
 * Author: athen
 * Date: 1/23/2024
 * Description:
 */

import com.widetech.menuapp.dto.responses.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getDescription(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleDataAccessException(DataAccessException ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getDescription(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ApiErrorResponse> handleSystemException(SystemException ex) {
        ApiErrorResponse response = new ApiErrorResponse(
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getDescription(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
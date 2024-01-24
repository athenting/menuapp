package com.widetech.menuapp.dto.responses;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class AbstractResponse<T> implements Serializable {

    private final String code;
    private final String message;

    private T data;

    public AbstractResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public AbstractResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

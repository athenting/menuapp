package com.widetech.menuapp.exception;

import com.widetech.menuapp.constants.ErrorCode;

public abstract class AbstractException extends RuntimeException {
    private final ErrorCode errorCode;

    public AbstractException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
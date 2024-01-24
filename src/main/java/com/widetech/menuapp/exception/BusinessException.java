package com.widetech.menuapp.exception;

import com.widetech.menuapp.constants.ErrorCode;

public class BusinessException extends AbstractException {
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
package com.widetech.menuapp.exception;

import com.widetech.menuapp.constants.ErrorCode;

public class DataAccessException extends AbstractException {
    public DataAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
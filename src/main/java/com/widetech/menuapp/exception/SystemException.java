package com.widetech.menuapp.exception;

import com.widetech.menuapp.constants.ErrorCode;


public class SystemException extends AbstractException {
    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
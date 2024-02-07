package com.widetech.menuapp.constants;

/**
 *
 */

public enum ErrorCode {
    // Business error codes
    SUCCESS("A1", "Business operated successfully."),
    BUSINESS_ERROR_A2("A2", "Business error description 2"),
    // other business errors ...

    // External resources and Database error codes
    EXTERNAL_DATABASE_ERROR_B1("B1", "External/Database error description 1"),
    EXTERNAL_DATABASE_ERROR_B2("B2", "External/Database error description 2"),
    // other database errors ...

    // System error codes
    SYSTEM_ERROR("C1", "System error description 1"),
    SYSTEM_ERROR_C2("C2", "System error description 2");
    // other system errors ...

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}





package com.widetech.menuapp.constants;

/**
 *
 */

public enum ErrorCode {
    // Business error codes
    SUCCESS("A1", "Business operated successfully."),
    ENTITY_NOT_FOUND("A2", "Entity not found"),

    PASSWORD_ENCODE_ERROR("A2", "Password encode fails"),

    ADMIN_NOT_EXIST("A3", "Admin does not exist"),

    USER_PASSWORD_ERROR("A4", "Password does not match"),

    ADMIN_STATUS_ERROR("A5", "Admin status not correct"),

    EMAIL_ALREADY_REGISTERED("A6", "Email has already been registered"),

    ITEM_NAME_ALREADY_REGISTERED("A7", "Item name has already been registered"),

    PHONE_ALREADY_REGISTERED("A8", "PhoneNumber has already been registered"),

    PARAM_ERROR("A9", "Parameter error"),

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





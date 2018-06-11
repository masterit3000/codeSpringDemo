package com.elearningbackend.utility;

/**
 * Created by dohalong on 02/12/2017.
 */
public enum ResultCodes {
    OK("200", "OK"),
    FAIL_UNRECOGNIZED_ERROR("999","UNRECOGNIZED ERROR");

    private final String code;
    private final String message;

    ResultCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

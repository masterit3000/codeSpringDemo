package com.elearningbackend.customexception;

public class ElearningException extends Exception{
    private static final long serialVersionUID = 1L;

    private String errorCode;
    public ElearningException(String message) {
        super(message);
    }

    public ElearningException(String errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

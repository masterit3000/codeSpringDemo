package com.elearningbackend.customexception;

import org.springframework.security.core.AuthenticationException;

public class ElearningAuthException extends AuthenticationException {
    public ElearningAuthException(String msg, Throwable t) {
        super(msg, t);
    }

    private static final long serialVersionUID = 1L;

    private String errorCode;
    public ElearningAuthException(String message) {
        super(message);
    }

    public ElearningAuthException(String errorCode, String message) {
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

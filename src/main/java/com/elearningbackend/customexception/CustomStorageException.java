package com.elearningbackend.customexception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by dohalong on 11/12/2017.
 */
public class CustomStorageException extends Exception {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String errorCode;
    public CustomStorageException(String message) {
        super(message);
    }

    public CustomStorageException(String errorCode, String message) {
        super(message);
        this.setErrorCode(errorCode);
    }
}

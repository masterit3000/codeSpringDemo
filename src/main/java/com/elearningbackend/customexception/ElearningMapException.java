package com.elearningbackend.customexception;

import lombok.Data;

import java.util.Map;

@Data
public class ElearningMapException extends ElearningException {
    private Map<Integer,ElearningException> exceptionMap;

    public ElearningMapException(String message) {
        super(message);
    }

    public ElearningMapException(Map<Integer,ElearningException> exceptionMap,String errorCode,String message){
        super(errorCode,message);
        this.exceptionMap = exceptionMap;
    }
}

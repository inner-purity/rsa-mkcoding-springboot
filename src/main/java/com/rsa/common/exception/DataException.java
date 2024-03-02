package com.rsa.common.exception;

import org.springframework.stereotype.Component;


public class DataException extends RuntimeException{
    private Integer code;

    private String exceptionClassName;

    private String exceptionMethodName;

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public String getExceptionMethodName() {
        return exceptionMethodName;
    }

    public DataException(String exceptionClassName, String exceptionMethodName, String message) {
        super(message);
        this.exceptionClassName = exceptionClassName;
        this.exceptionMethodName = exceptionMethodName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public DataException(String message) {
        super(message);
    }
    public DataException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public DataException(Integer code, String msg, Throwable cause){
        super(msg, cause);
        this.code = code;
    }
}

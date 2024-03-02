package com.rsa.common.exception;

public class BusinessException extends RuntimeException{
    private Integer code;

    private String exceptionClassName;

    private String exceptionMethodName;

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public String getExceptionMethodName() {
        return exceptionMethodName;
    }

    public void setExceptionClassName(String exceptionClassName){
        this.exceptionClassName = exceptionClassName;
    }

    public void setExceptionMethodName(String exceptionMethodName){
        this.exceptionMethodName = exceptionMethodName;
    }

    public BusinessException(Integer code, String exceptionClassName, String exceptionMethodName, String message) {
        super(message);
        this.code = code;
        this.exceptionClassName = exceptionClassName;
        this.exceptionMethodName = exceptionMethodName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}

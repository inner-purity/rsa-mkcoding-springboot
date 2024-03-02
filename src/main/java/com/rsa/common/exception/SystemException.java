package com.rsa.common.exception;

public class SystemException extends RuntimeException{
    private Integer code;

    private String exceptionClassName;

    private String exceptionMethodName;

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public String getExceptionMethodName() {
        return exceptionMethodName;
    }

    public SystemException(String exceptionClassName, String exceptionMethodName, String message) {
        super(message);
        this.exceptionClassName = exceptionClassName;
        this.exceptionMethodName = exceptionMethodName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SystemException(String message) {
        super(message);
    }
    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}

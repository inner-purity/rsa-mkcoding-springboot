package com.rsa.handler;

import com.rsa.annotation.LogDoExceptionException;
import com.rsa.common.ExceptionClass;
import com.rsa.common.exception.DataException;
import com.rsa.common.exception.DoExceptionException;
import com.rsa.common.utils.AliOssUtil;
import com.rsa.common.utils.IpUtil;
import com.rsa.result.Result;
import com.rsa.common.exception.BusinessException;
import com.rsa.common.exception.SystemException;
import com.rsa.service.LogExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class ProjectExceptionAdvice {
    @Autowired
    AliOssUtil aliOssUtil;

    @Autowired
    LogExceptionService logExceptionService;

    @LogDoExceptionException
    @ExceptionHandler(BusinessException.class)
    //不记录异常日志，单纯返回结果给用户
    public Result doBusinessException(BusinessException ex, HttpServletRequest request) {
        log.info("正在统一处理异常:{}", ex.getMessage());
        String requestPath = request.getRequestURI();
        String ipAddress = IpUtil.getIp(request);
        String exceptionClass = ExceptionClass.BUSINESS_EXCEPTION;
        String className = ex.getExceptionClassName();
        String methodName = ex.getExceptionMethodName();
        String exceptionMessage = ex.getMessage();
        logExceptionService.logException(requestPath, ipAddress, exceptionClass, className, methodName, exceptionMessage);

        return Result.exception(ex.getCode(), ex.getMessage());
    }

    @LogDoExceptionException
    @ExceptionHandler(DataException.class)
    public Result doDataException(DataException ex, HttpServletRequest request) {
        log.info("正在统一处理异常:{}", ex.getMessage());
        String requestPath = request.getRequestURI();
        String ipAddress = IpUtil.getIp(request);
        String exceptionClass = ExceptionClass.DATA_EXCEPTION;
        String className = ex.getExceptionClassName();
        String methodName = ex.getExceptionMethodName();
        String exceptionMessage = ex.getMessage();
        logExceptionService.logException(requestPath, ipAddress, exceptionClass, className, methodName, exceptionMessage);

        return Result.error("数据处理异常，请检查数据格式或稍候再试");
    }

    //特殊：用于处理异常处理类产生的异常，防止自身产生异常导致异常记录及处理系统崩溃
    @LogDoExceptionException
    @ExceptionHandler(DoExceptionException.class)
    public void doDoExceptionException(DoExceptionException ex, HttpServletRequest request) {
        log.info("正在统一处理异常:{}", ex.getMessage());
        String requestPath = request.getRequestURI();
        String ipAddress = IpUtil.getIp(request);
        String exceptionClass = ExceptionClass.EXCEPTION_EXCEPTION;
        String className = ex.getExceptionClassName();
        String methodName = ex.getExceptionMethodName();
        String exceptionMessage = ex.getMessage();
        logExceptionService.logException(requestPath, ipAddress, exceptionClass, className, methodName, exceptionMessage);
    }

    @LogDoExceptionException
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex, HttpServletRequest request) {
        log.info("正在统一处理异常:{}", ex.getMessage());
        String requestPath = request.getRequestURI();
        String ipAddress = IpUtil.getIp(request);
        String exceptionClass = ExceptionClass.SYSTEM_EXCEPTION;
        String className = ex.getExceptionClassName();
        String methodName = ex.getExceptionMethodName();
        String exceptionMessage = ex.getMessage();
        logExceptionService.logException(requestPath, ipAddress, exceptionClass, className, methodName, exceptionMessage);

        return Result.error("服务器异常，请稍后再试");
    }

    @LogDoExceptionException
    @ExceptionHandler(Exception.class)
    //暂且不做异常记录
    public Result doOtherException(Exception ex) {
        log.info("正在统一处理异常:{}", ex.getMessage());
        return Result.error("服务器繁忙，请稍后再试");
    }
}

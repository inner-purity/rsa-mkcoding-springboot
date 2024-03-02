package com.rsa.aspect;

import com.rsa.common.exception.BusinessException;
import com.rsa.common.exception.DataException;
import com.rsa.common.exception.DoExceptionException;
import com.rsa.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 异常日志记录切面类
 */
@Aspect
@Component
@Slf4j
public class LogExceptionAspect {

    @Pointcut("@annotation(com.rsa.annotation.LogDataException)")
    public void logDataExceptionPointCut() {}

    @Pointcut("@annotation(com.rsa.annotation.LogDoExceptionException)")
    public void logDoExceptionExceptionPointCut() {}

    @Pointcut("@annotation(com.rsa.annotation.LogSystemException)")
    public void logSystemExceptionPointCut() {}


    @Around("logDataExceptionPointCut()")
    public Object logDataException(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("AOP正在自动抛出异常");
        try {
            return proceedingJoinPoint.proceed();
        }
        catch (BusinessException e){
            e.setExceptionClassName(proceedingJoinPoint.getSignature().getDeclaringTypeName());
            e.setExceptionMethodName(proceedingJoinPoint.getSignature().getName());
            throw e;
        }
        catch (Throwable e) {
            String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            log.info("AOP捕获到了异常并抛出.....{}",e.getMessage());
            throw new DataException(className,methodName,e.getMessage());
        }
    }

    @Around("logDoExceptionExceptionPointCut()")
    public Object logDoExceptionException(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("AOP正在自动抛出异常");
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            log.info("AOP捕获到了异常并抛出.....{}",e.getMessage());
            throw new DoExceptionException(className,methodName,e.getMessage());
        }
    }

    @Around("logSystemExceptionPointCut()")
    public Object logSystemException(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("AOP正在自动抛出异常");
        try {
            return proceedingJoinPoint.proceed();
        }
        catch (BusinessException e){
            e.setExceptionClassName(proceedingJoinPoint.getSignature().getDeclaringTypeName());
            e.setExceptionMethodName(proceedingJoinPoint.getSignature().getName());
            throw e;
        }
        catch (Throwable e) {
            String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            log.info("AOP捕获到了异常并抛出.....{}",e.getMessage());
            throw new SystemException(className,methodName,e.getMessage());
        }
    }
}

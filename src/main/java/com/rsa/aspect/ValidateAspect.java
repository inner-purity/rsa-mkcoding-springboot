package com.rsa.aspect;


import com.rsa.common.Code;
import com.rsa.common.ExceptionMessage;
import com.rsa.common.exception.BusinessException;
import com.rsa.context.BaseContext;
import com.rsa.entity.User;
import com.rsa.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 鉴权切面类
 */
@Aspect
@Component
@Slf4j
public class ValidateAspect {
    @Autowired
    UserMapper userMapper;
    @Pointcut("execution(* com.rsa.controller.*.*(..)) && @annotation(com.rsa.annotation.Validate))")
    public void checkValidatePointCut(){}

    @Before("checkValidatePointCut()")
    public void checkValidate(){
        log.info("AOP自动鉴权中......");
        Integer id = BaseContext.getCurrentId();
        User checkUser = userMapper.getById(id);
        if(checkUser.getValidate() < 2){
            throw new BusinessException(Code.NO_VALIDATE, ExceptionMessage.NO_AUTH_TODO);
        }
    }
}

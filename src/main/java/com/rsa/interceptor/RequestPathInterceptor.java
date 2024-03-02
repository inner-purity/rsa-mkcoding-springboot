package com.rsa.interceptor;

import com.rsa.annotation.LogSystemException;
import com.rsa.common.Code;
import com.rsa.common.ExceptionMessage;
import com.rsa.common.exception.BusinessException;
import com.rsa.service.RequestPathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class RequestPathInterceptor implements HandlerInterceptor {

    @Autowired
    RequestPathService requestPathService;

    @Override
    @LogSystemException
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("正在注册请求拦截器......");
        String requestURI = request.getRequestURI();
        boolean flag = requestPathService.checkByRequestPath(requestURI);
        if(!flag){
            throw new BusinessException(Code.API_LOCKED, ExceptionMessage.REQUEST_PATH_BANNED);
        }
        return true;
    }
}

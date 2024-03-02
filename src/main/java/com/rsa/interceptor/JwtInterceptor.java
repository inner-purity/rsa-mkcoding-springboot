package com.rsa.interceptor;

import com.rsa.common.properties.JwtProperties;
import com.rsa.common.utils.JwtUtil;
import com.rsa.context.BaseContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    JwtProperties jwtProperties;
    @Override
    @CrossOrigin(origins = "*")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = request.getHeader(jwtProperties.getTokenName());

        try {
            log.info("jwt校验： {}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(),token);
            log.info("claims:{}",claims);
            Integer id = Integer.valueOf(claims.get("id").toString());
            log.info("id" + claims.get("id").toString());
            log.info("当前员工id:", id);
            BaseContext.setCurrentId(id);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(403);
            return false;
        }
    }
}

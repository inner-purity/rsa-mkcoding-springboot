package com.rsa.interceptor;

import com.rsa.annotation.LogSystemException;
import com.rsa.common.Code;
import com.rsa.common.ExceptionMessage;
import com.rsa.common.exception.BusinessException;
import com.rsa.common.utils.IpUtil;
import com.rsa.entity.RequestIp;
import com.rsa.mapper.RequestIpMapper;
import com.rsa.service.IpAddressService;
import com.rsa.service.RequestIpService;
import com.rsa.service.RequestPathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
@Slf4j
public class IpInterceptor implements HandlerInterceptor {

    //    @Autowired
//    private
    @Autowired
    RequestIpService requestIpService;
    @Autowired
    IpAddressService ipAddressService;
    @Autowired
    RequestPathService requestPathService;

    @Override
    @LogSystemException
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("来访ip,请求路径---正在记录......");
        String ipAddress = IpUtil.getIp(request);
        String requestURI = request.getRequestURI();

        log.info("获取到的ip为......:{}", ipAddress);
        log.info("请求路径为......:{}", requestURI);
        boolean flag = ipAddressService.checkByIpName(ipAddress);
        if(!flag){
            throw new BusinessException(Code.IP_FORBIDDEN, ExceptionMessage.IP_HAD_BEEN_BANNED);
        }
        boolean shouldBeLogged = requestPathService.checkNeedWatch(requestURI,ipAddress);
        ////实现ip_address验证和ip_address自动添加///////////////
        if(shouldBeLogged){
            RequestIp requestIp = new RequestIp();
            requestIp.setIpAddress(ipAddress);
            requestIp.setRequestPath(requestURI);
            requestIp.setRequestTime(LocalDateTime.now());
            requestIpService.insertRequestIp(requestIp);
            log.info("--------来访ip已注入数据库-------");
        }
        return true;
    }
}

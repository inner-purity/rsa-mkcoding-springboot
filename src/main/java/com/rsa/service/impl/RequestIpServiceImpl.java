package com.rsa.service.impl;

import com.rsa.annotation.LogDataException;
import com.rsa.entity.RequestIp;
import com.rsa.mapper.RequestIpMapper;
import com.rsa.service.RequestIpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Slf4j
@Transactional
public class RequestIpServiceImpl implements RequestIpService {
    @Autowired
    RequestIpMapper requestIpMapper;

    /**
     * 插入ip数据
     */
    @LogDataException
    public void insertRequestIp(RequestIp requestIp){
        requestIpMapper.insertRequestIp(requestIp);
    };
}

package com.rsa.service.impl;

import com.rsa.annotation.LogDataException;
import com.rsa.entity.IpAddress;
import com.rsa.mapper.IpAddressMapper;
import com.rsa.service.IpAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Slf4j
@Transactional
public class IpAddressServiceImpl implements IpAddressService {
    @Autowired
    IpAddressMapper ipAddressMapper;

    /**
     * 检查来访ip是否许可接入使用
     *
     * @param ipAddress
     * @return
     */
    @Override
    @LogDataException
    public boolean checkByIpName(String ipAddress) {
        Integer canUse = ipAddressMapper.checkByIpName(ipAddress);
        if(canUse == null){
            IpAddress ipAddressInsert = new IpAddress();
            ipAddressInsert.setIpName(ipAddress);
            ipAddressInsert.setCanUse(1);
            ipAddressInsert.setUpdateUser("系统/auto记录");
            ipAddressInsert.setUpdateTime(LocalDateTime.now());
            ipAddressMapper.insertIpAddress(ipAddressInsert);
            return true;
        }else if(canUse == 1){
            return true;
        }
        return false;
    }
}

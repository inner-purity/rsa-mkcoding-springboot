package com.rsa.service;


public interface IpAddressService {
    /**
     * 检查来访ip是否许可接入使用
     *
     * @param ipAddress
     * @return
     */
    boolean checkByIpName(String ipAddress);
}

package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.entity.RequestIp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequestIpMapper extends BaseMapper<RequestIp> {

    @Insert("insert into request_ip (ip_address, request_time, request_path) VALUES (#{ipAddress},#{requestTime},#{requestPath})")
    void insertRequestIp(RequestIp requestIp);
}

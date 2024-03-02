package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.entity.IpAddress;
import com.rsa.entity.RequestIp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IpAddressMapper extends BaseMapper<IpAddress> {

    @Select("select can_use from ip_address where ip_name = #{ipAddress}")
    Integer checkByIpName(String ipAddress);

    @Insert("insert into ip_address (ip_name, can_use, update_user, update_time)" +
            " VALUES (#{ipName},#{canUse},#{updateUser},#{updateTime})")
    void insertIpAddress(IpAddress ipAddressInsert);

    @Select("select watch_it from ip_address where ip_name = #{ipAddress}")
    Integer watchByIpName(String ipAddress);
}

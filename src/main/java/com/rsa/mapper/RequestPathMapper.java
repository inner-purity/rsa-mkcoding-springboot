package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.rsa.dto.RequestPathPageDTO;
import com.rsa.entity.RequestIp;
import com.rsa.entity.RequestPath;
import com.rsa.entity.RsaKey;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RequestPathMapper extends BaseMapper<RequestPath> {

    @Select("select can_request from request_path where request_path = #{requestURI}")
    Integer checkByRequestPath(String requestURI);

    @Select("select watch_it from request_path where request_path = #{requestURI}")
    Integer checkNeedWatch(String requestURI);


    Page<RequestPath> pageQuery(RequestPathPageDTO requestPathPageDTO);

    void updateRequestPathById(RequestPath requestPath);

    @Select("select * from request_path where id = #{id}")
    RequestPath getById(Long id);

    @Insert("insert into request_path " +
            "(request_path, note, can_request, watch_it, update_user, update_time)" +
            " VALUES (#{requestPath}, #{note}, #{canRequest}, #{watchIt}, #{updateUser}, #{updateTime})")
    void addRequestPath(RequestPath requestPath);

    @Select("select * from request_path where request_path = #{requestPath}")
    RequestPath getByRequest(String requestPath);
}

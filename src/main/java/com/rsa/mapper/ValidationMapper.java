package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.entity.IpAddress;
import com.rsa.entity.Validation;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface ValidationMapper extends BaseMapper<Validation> {

    @Select("select id from validation where email = #{email} and type = #{type} and time > #{dateTime}")
    Long checkSame(@Param("email") String email,@Param("type") Integer type,@Param("dateTime") Date dateTime);

    @Delete("delete from validation where email = #{email} and type = #{type}")
    void remove(@Param("email") String email,@Param("type") Integer type);

    @Select("select * from validation where email = #{email} and type = #{type}")
    Validation getByEmailAndType(@Param("email") String email,@Param("type") Integer type);
}

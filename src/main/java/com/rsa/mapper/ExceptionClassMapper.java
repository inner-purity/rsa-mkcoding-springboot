package com.rsa.mapper;

import com.rsa.entity.ExceptionClass;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExceptionClassMapper {
    @Select("select id from exception_class where exception_name = #{exceptionClass}")
    Integer getByClass(String exceptionClass);

    @Insert("insert into exception_class (exception_name) values (#{exceptionClass})")
    void insert(String exceptionClass);
}

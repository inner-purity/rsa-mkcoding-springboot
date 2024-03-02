package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.dto.LogExceptionDTO;
import com.rsa.entity.LogException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogExceptionMapper extends BaseMapper<LogException> {

    @Select("select le.id,log_time,request_path,request_ip,exception_name as exception_class,class_name,method_name,exception_message from log_exception le" +
            " left join exception_class ec on ec.id = le.exception_class_id")
    List<LogExceptionDTO> getAll();

}

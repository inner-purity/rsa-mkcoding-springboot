package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.rsa.dto.RsaKeyDTO;
import com.rsa.dto.RsaKeyPageDTO;
import com.rsa.entity.RsaKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RsaMapper extends BaseMapper<RsaKey> {
    void insert(RsaKeyDTO rsaKeyDTO);

    @Select("select store_time,key_type,key_force,note,private_key,public_key,private_password,id,user_id" +
            " from rsa_key where user_id = #{userId}")
    List<RsaKey> getUserRsaKeyByUserId(Integer userId);

    Page<RsaKey> pageQuery(RsaKeyPageDTO rsaKeyPageDTO);

    @Select("select * from rsa_key where id = #{id}")
    RsaKey getUserRsaKeyByKeyId(Integer id);
}

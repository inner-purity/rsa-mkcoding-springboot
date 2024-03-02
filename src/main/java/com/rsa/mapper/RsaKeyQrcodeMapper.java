package com.rsa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.dto.RsaKeyQrcodeDTO;
import com.rsa.entity.RsaKeyQrcode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface RsaKeyQrcodeMapper extends BaseMapper<RsaKeyQrcode> {

    @Select("delete from rsa_key_qrcode where store_user_id = #{currentId}")
    void deleteByUserId(Integer currentId);

    @Select("select * from rsa_key_qrcode where unicode = #{unicode}")
    RsaKeyQrcodeDTO getByUnicode(String unicode);

    @Delete("delete from rsa_key_qrcode where store_time <= #{time}")
    void deleteByTime(LocalDateTime time);
}

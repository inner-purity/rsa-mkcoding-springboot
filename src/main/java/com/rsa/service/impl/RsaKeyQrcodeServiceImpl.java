package com.rsa.service.impl;

import com.rsa.common.Code;
import com.rsa.common.ExceptionMessage;
import com.rsa.common.exception.BusinessException;
import com.rsa.context.BaseContext;
import com.rsa.dto.RsaKeyQrcodeDTO;
import com.rsa.entity.RsaKeyQrcode;
import com.rsa.mapper.RsaKeyQrcodeMapper;
import com.rsa.mapper.UserMapper;
import com.rsa.service.RsaKeyQrcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RsaKeyQrcodeServiceImpl implements RsaKeyQrcodeService {
    @Autowired
    private RsaKeyQrcodeMapper rsaKeyQrcodeMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 插入限时二维码并返回随机访问路径码
     *
     * @param rsaKeyQrcode
     * @return
     */
    @Override
    public String insert(RsaKeyQrcode rsaKeyQrcode) {
        rsaKeyQrcodeMapper.deleteByUserId(BaseContext.getCurrentId());
        String unicode = UUID.randomUUID().toString();
        rsaKeyQrcode.setUnicode(unicode);
        rsaKeyQrcode.setStoreUserId(BaseContext.getCurrentId());
        rsaKeyQrcodeMapper.insert(rsaKeyQrcode);
        return unicode;
    }

    /**
     * 获取二维码携带的密钥数据
     * @param unicode
     * @return
     */
    @Override
    public RsaKeyQrcodeDTO getByUnicode(String unicode) {
        RsaKeyQrcodeDTO rsaKeyQrcodeDTO = rsaKeyQrcodeMapper.getByUnicode(unicode);
        if(rsaKeyQrcodeDTO == null){
            throw new BusinessException(Code.QRCODE_HAD_BEEN_DISABLED, ExceptionMessage.QRCODE_HAD_BEEN_DISABLED);
        }
        rsaKeyQrcodeDTO.setUsername(userMapper.getById(BaseContext.getCurrentId()).getUsername());
        return rsaKeyQrcodeDTO;
    }
}

package com.rsa.service;

import com.rsa.dto.RsaKeyQrcodeDTO;
import com.rsa.entity.RsaKeyQrcode;

public interface RsaKeyQrcodeService {

    /**
     * 插入限时二维码并返回随机访问路径码
     *
     * @param rsaKeyQrcode
     * @return
     */
    String insert(RsaKeyQrcode rsaKeyQrcode);

    /**
     * 获取二维码携带的密钥数据
     * @param unicode
     * @return
     */
    RsaKeyQrcodeDTO getByUnicode(String unicode);
}

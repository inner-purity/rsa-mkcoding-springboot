package com.rsa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.dto.RsaKeyDTO;
import com.rsa.dto.RsaKeyPageDTO;
import com.rsa.entity.RsaKey;
import com.rsa.result.PageResult;

import java.util.List;

public interface RsaService extends IService<RsaKey> {

    /**
     * 上传要保存的RSA密钥生成记录
     * @param rsaKeyDTO
     * @return
     */
    void uploadRsaKey(RsaKeyDTO rsaKeyDTO);

    /**
     * 获取用户RSA密钥数据记录
     *
     * @return
     */
    List<RsaKey> getUserRsaKeyByUserId();

    /**
     * 删除用户指定的RSA密钥数据
     * @param rsaKeyList
     * @return
     */
    void deleteBatchIds(List<RsaKey> rsaKeyList);

    /**
     * 删除用户指定的单条RSA密钥数据
     *
     * @param id
     * @return
     */
    void deleteByUserId(Integer id);

    /**
     * 分页查询密钥数据
     * @param rsaKeyPageDTO
     */
    PageResult pageQueryRsaKey(RsaKeyPageDTO rsaKeyPageDTO);

    /**
     * 获取单条密钥数据
     * @param id
     * @return
     */
    RsaKey getUserRsaKeyByKeyId(Integer id);
}

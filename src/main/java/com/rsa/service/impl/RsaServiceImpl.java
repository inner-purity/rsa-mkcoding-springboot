package com.rsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsa.annotation.LogDataException;
import com.rsa.dto.RsaKeyDTO;
import com.rsa.context.BaseContext;
import com.rsa.dto.RsaKeyPageDTO;
import com.rsa.entity.RsaKey;
import com.rsa.entity.User;
import com.rsa.mapper.RsaMapper;
import com.rsa.result.PageResult;
import com.rsa.service.RsaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@Transactional
public class RsaServiceImpl extends ServiceImpl<RsaMapper, RsaKey> implements RsaService {

    @Autowired
    RsaMapper rsaMapper;

    /**
     * 上传要保存的RSA密钥生成记录
     *
     * @param rsaKeyDTO
     * @return
     */
    @Override
    @LogDataException
    public void uploadRsaKey(RsaKeyDTO rsaKeyDTO) {
        rsaKeyDTO.setUserId(BaseContext.getCurrentId());
        rsaKeyDTO.setStoreTime(LocalDateTime.now());
        rsaMapper.insert(rsaKeyDTO);
    }

    /**
     * 获取用户RSA密钥数据记录
     *
     * @return
     */
    @Override
    @LogDataException
    public List<RsaKey> getUserRsaKeyByUserId() {
        List<RsaKey> rsaKeyList = rsaMapper.getUserRsaKeyByUserId(BaseContext.getCurrentId());
        return rsaKeyList;
    }

    /**
     * 删除用户指定的RSA密钥数据
     * @param rsaKeyList
     * @return
     */
    @Override
    @LogDataException
    public void deleteBatchIds(List<RsaKey> rsaKeyList) {
        List<Integer> idList = new ArrayList<>();
        rsaKeyList.forEach(item -> {
            idList.add(item.getId());
        });
        rsaMapper.deleteBatchIds(idList);
    }

    /**
     * 删除用户指定的单条RSA密钥数据
     *
     * @param id
     * @return
     */
    @Override
    @LogDataException
    public void deleteByUserId(Integer id) {
        rsaMapper.deleteById(id);
    }

    /**
     * 分页查询密钥数据
     * @param rsaKeyPageDTO
     */
    @Override
    public PageResult pageQueryRsaKey(RsaKeyPageDTO rsaKeyPageDTO) {
        PageHelper.startPage(rsaKeyPageDTO.getPageNum(), rsaKeyPageDTO.getPageSize());
        rsaKeyPageDTO.setUserId(BaseContext.getCurrentId());
        Page<RsaKey> rsaKeyPage = rsaMapper.pageQuery(rsaKeyPageDTO);
        Long total = rsaKeyPage.getTotal();
        List<RsaKey> result = rsaKeyPage.getResult();
        log.info("result:{},getResult:{}", result, rsaKeyPage.getResult());
        PageResult pageResult = new PageResult(total, result);
        return pageResult;
    }

    /**
     * 获取单条密钥数据
     * @param id
     * @return
     */
    @Override
    public RsaKey getUserRsaKeyByKeyId(Integer id) {
        return rsaMapper.getUserRsaKeyByKeyId(id);
    }
}

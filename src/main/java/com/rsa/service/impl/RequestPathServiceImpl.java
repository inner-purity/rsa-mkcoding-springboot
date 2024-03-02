package com.rsa.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rsa.annotation.LogDataException;
import com.rsa.common.Code;
import com.rsa.common.ExceptionMessage;
import com.rsa.common.exception.BusinessException;
import com.rsa.context.BaseContext;
import com.rsa.dto.RequestPathPageDTO;
import com.rsa.entity.RequestPath;
import com.rsa.entity.User;
import com.rsa.mapper.IpAddressMapper;
import com.rsa.mapper.RequestPathMapper;
import com.rsa.mapper.UserMapper;
import com.rsa.result.PageResult;
import com.rsa.service.RequestPathService;
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
public class RequestPathServiceImpl implements RequestPathService {
    @Autowired
    RequestPathMapper requestPathMapper;

    @Autowired
    IpAddressMapper ipAddressMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 查询接口路径的启用禁用状态
     */
    @Override
    @LogDataException
    public boolean checkByRequestPath(String requestURI) {
        Integer canRequest = requestPathMapper.checkByRequestPath(requestURI);
        if (canRequest == null || canRequest == 1) {
            return true;
        }
        return false;
    }

    /**
     * 检查对应端口是否需要被监视
     *
     * @return
     */
    @Override
    @LogDataException
    public boolean checkNeedWatch(String requestURI, String ipAddress) {
        //检查该请求路径是否需要被监视
        Integer needWatch = requestPathMapper.checkNeedWatch(requestURI);
        if (needWatch == null || needWatch == 0) {
            return false;
        } else if (needWatch == 1) {
            //检查来访ip是否需要监视
            Integer watchIt = ipAddressMapper.watchByIpName(ipAddress);
            if (watchIt == null || watchIt == 1) {
                return true;
            } else if (watchIt == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 分页查询所有接口路径
     *
     * @param requestPathPageDTO
     * @return
     */
    @Override
    @LogDataException
    public PageResult getRequestPathByPage(RequestPathPageDTO requestPathPageDTO) {
        log.info("用户分页查询给出的requestPathPageDTO:{}", requestPathPageDTO);
        PageHelper.startPage(requestPathPageDTO.getPage(), requestPathPageDTO.getPageSize());
        Page<RequestPath> page = requestPathMapper.pageQuery(requestPathPageDTO);
        Long total = page.getTotal();
        log.info("total:{}", total);
        List<RequestPath> records = page.getResult();
        PageResult pageResult = new PageResult(total, records);
        return pageResult;
    }

    /**
     * 更新接口路径参数
     *
     * @param requestPath
     * @return
     */
    @Override
    @LogDataException
    public void updateRequestPath(RequestPath requestPath) {
        Integer id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);
        requestPath.setUpdateUser(user.getUsername());
        if (requestPath.getRequestPath() != null) {
            RequestPath checkRequestPath = requestPathMapper.getById(requestPath.getId());
            if (checkRequestPath != null && checkRequestPath.getRequestPath().equals(requestPath.getRequestPath())) {
                throw new BusinessException(Code.HAD_BEEN_ADD, ExceptionMessage.HAD_BEEN_ADD);
            }
        }
        requestPath.setUpdateTime(LocalDateTime.now());
        requestPathMapper.updateRequestPathById(requestPath);
    }

    /**
     * 删除单条或批量删除接口路径配置
     *
     * @param requestPathList
     * @return
     */
    @Override
    @LogDataException
    public void deleteBatch(List<RequestPath> requestPathList) {
        List<Long> idlist = new ArrayList<>();
        for (RequestPath requestPath : requestPathList) {
            idlist.add(requestPath.getId());
        }
        requestPathMapper.deleteBatchIds(idlist);
    }

    /**
     * 添加接口配置
     *
     * @param requestPath
     * @return
     */
    @Override
    @LogDataException
    public void addRequestPath(RequestPath requestPath) {
        RequestPath checkRequestPath = requestPathMapper.getByRequest(requestPath.getRequestPath());
        if (checkRequestPath != null && checkRequestPath.getRequestPath().equals(requestPath.getRequestPath())) {
            throw new BusinessException(Code.HAD_BEEN_ADD, ExceptionMessage.HAD_BEEN_ADD);
        }
        Integer id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);
        requestPath.setUpdateUser(user.getUsername());
        requestPath.setUpdateTime(LocalDateTime.now());
        requestPathMapper.addRequestPath(requestPath);
    }

    /**
     * 批量更新接口配置
     *
     * @param requestPathList
     * @return
     */
    @Override
    @LogDataException
    public void updateBatch(List<RequestPath> requestPathList) {
        Integer id = BaseContext.getCurrentId();
        User user = userMapper.getById(id);
        for (RequestPath requestPath : requestPathList) {
            requestPath.setUpdateUser(user.getUsername());
            requestPath.setUpdateTime(LocalDateTime.now());
            requestPathMapper.updateRequestPathById(requestPath);
        }

    }
}

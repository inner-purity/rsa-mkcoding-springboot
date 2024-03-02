package com.rsa.service;

import com.rsa.dto.RequestPathPageDTO;
import com.rsa.entity.RequestPath;
import com.rsa.result.PageResult;

import java.util.List;

public interface RequestPathService {

    /**
     * 查询接口路径的启用禁用状态
     */
    boolean checkByRequestPath(String requestURI);

    /**
     * 检查对应端口是否需要被监视
     * @return
     */
    boolean checkNeedWatch(String requestURI,String ipAddress);

    /**
     * 分页查询所有接口路径
     * @param requestPathPageDTO
     * @return
     */
    PageResult getRequestPathByPage(RequestPathPageDTO requestPathPageDTO);

    /**
     * 更新接口路径参数
     * @param requestPath
     * @return
     */
    void updateRequestPath(RequestPath requestPath);

    /**
     * 删除单条或批量删除接口路径配置
     * @param requestPathList
     * @return
     */
    void deleteBatch(List<RequestPath> requestPathList);


    /**
     *添加接口配置
     * @param requestPath
     * @return
     */
    void addRequestPath(RequestPath requestPath);

    /**
     * 批量更新接口配置
     * @param requestPathList
     * @return
     */
    void updateBatch(List<RequestPath> requestPathList);
}

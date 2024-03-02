package com.rsa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.dto.LogExceptionDTO;
import com.rsa.entity.LogException;

import java.util.List;


public interface LogExceptionService extends IService<LogException> {
    /**
     * 记录异常日志
     * @param requestPath
     * @param ipAddress
     * @param exceptionClass
     * @param exceptionMessage
     */

    void logException(String requestPath, String ipAddress, String exceptionClass, String className, String methodName, String exceptionMessage);

    /**
     * 获取所有异常日志
     * @return
     */
    List<LogExceptionDTO> getAll();
}

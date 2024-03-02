package com.rsa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.annotation.LogDataException;
import com.rsa.common.exception.DataException;
import com.rsa.dto.LogExceptionDTO;
import com.rsa.entity.LogException;
import com.rsa.mapper.ExceptionClassMapper;
import com.rsa.mapper.LogExceptionMapper;
import com.rsa.service.LogExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogExceptionServiceImpl extends ServiceImpl<LogExceptionMapper, LogException> implements LogExceptionService {

    @Autowired
    LogExceptionMapper logExceptionMapper;
    @Autowired
    ExceptionClassMapper exceptionClassMapper;

    /**
     * 记录异常日志
     *
     * @param requestPath
     * @param ipAddress
     * @param exceptionClass
     * @param exceptionMessage
     */
    @Override
    @LogDataException
    public void logException(String requestPath, String ipAddress, String exceptionClass, String className, String methodName, String exceptionMessage) {
        try {
            Integer exceptionClassId = exceptionClassMapper.getByClass(exceptionClass);
            if (exceptionClassId == null) {
                exceptionClassMapper.insert(exceptionClass);
                exceptionClassId = exceptionClassMapper.getByClass(exceptionClass);
                //如果数据库还没有记录此次异常类型，那么先记录，再获取到ID进行exception_class_id存储
            }
            LogException logException = new LogException();
            logException.setRequestPath(requestPath);
            logException.setRequestIp(ipAddress);
            logException.setExceptionClassId(exceptionClassId);
            logException.setClassName(className);
            logException.setMethodName(methodName);
            logException.setExceptionMessage(exceptionMessage);
            logException.setLogTime(LocalDateTime.now());
            logExceptionMapper.insert(logException);
        } catch (Exception e) {
            throw new DataException(e.getMessage());
        }
    }

    /**
     * 获取所有异常日志
     *
     * @return
     */
    @Override
    @LogDataException
    public List<LogExceptionDTO> getAll() {
        try {
            List<LogExceptionDTO> logExceptionDTOList = logExceptionMapper.getAll();
            return logExceptionDTOList;
        } catch (Exception e) {
            throw new DataException(e.getMessage());
        }
    }
}

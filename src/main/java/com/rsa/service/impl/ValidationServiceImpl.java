package com.rsa.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.rsa.annotation.LogDataException;
import com.rsa.annotation.LogSystemException;
import com.rsa.common.Code;
import com.rsa.common.ExceptionMessage;
import com.rsa.common.exception.BusinessException;
import com.rsa.entity.User;
import com.rsa.entity.Validation;
import com.rsa.mapper.IpAddressMapper;
import com.rsa.mapper.ValidationMapper;
import com.rsa.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Slf4j
@Transactional
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    ValidationMapper validationMapper;

    @Override
    @LogSystemException
    public void save(String email, String code, Integer type, DateTime dateTime) {
        Validation validation = new Validation();
        validation.setEmail(email);
        validation.setCode(code);
        validation.setType(type);
        validation.setTime(dateTime);
        //删除之前同类型验证
        validationMapper.remove(email, type);
        //之后保存本次最新验证
        validationMapper.insert(validation);
    }

    @Override
    @LogSystemException
    public Long checkSame(String email, Integer type, Date dateTime) {
        Long id = validationMapper.checkSame(email, type, dateTime);
        return id;
    }

    @Override
    @LogDataException
    public void getValidation(User user) {
        Date now = new Date();
        String email = user.getEmail();
        String code = user.getCode();
        Integer type = user.getType();
        Validation validation = validationMapper.getByEmailAndType(email, type);
        if (validation == null) {
            throw new BusinessException(Code.EMAIL_ERROR, ExceptionMessage.EMAIL_ERROR);
        } else if (now.getTime() >= validation.getTime().getTime()) {
            throw new BusinessException(Code.CODE_OUTDATED, ExceptionMessage.CODE_OUTDATED);
        } else if (!validation.getCode().equals(code)) {
            throw new BusinessException(Code.EMAIL_CODE_ERROR, ExceptionMessage.EMAIL_CODE_ERROR);
        }
    }

    @Override
    public void removeByEmailAndType(User user) {
        validationMapper.remove(user.getEmail(), 2);
        //删除当前用户名下为登录性质的邮箱验证记录
    }
}

package com.rsa.service;


import cn.hutool.core.date.DateTime;
import com.rsa.entity.User;

import java.util.Date;

public interface ValidationService {

    void save(String email, String code, Integer type, DateTime dateTime);

    Long checkSame(String email, Integer type, Date dateTime);


    void getValidation(User user);

    void removeByEmailAndType(User user);
}

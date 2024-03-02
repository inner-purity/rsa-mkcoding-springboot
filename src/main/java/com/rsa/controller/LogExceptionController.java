package com.rsa.controller;

import com.rsa.dto.LogExceptionDTO;
import com.rsa.entity.LogException;
import com.rsa.result.Result;
import com.rsa.service.LogExceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/logException")
public class LogExceptionController {
    @Autowired
    LogExceptionService logExceptionService;

    /**
     * 获取所有异常日志
     * @return
     */
    @GetMapping("/getAll")
    public Result<List<LogExceptionDTO>> getAllLogException(){
        List<LogExceptionDTO> logExceptionDTOList = logExceptionService.getAll();
        return Result.success(logExceptionDTOList);
    }
}

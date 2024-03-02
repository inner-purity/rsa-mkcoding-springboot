package com.rsa.task;

import com.rsa.mapper.RsaKeyQrcodeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class ClearQrcodeStorageTask {

    @Autowired
    RsaKeyQrcodeMapper rsaKeyQrcodeMapper;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void clearQrcodeStorage(){
        log.info("定时任务(5min)------二维码过期清理---{}",LocalDateTime.now());
        rsaKeyQrcodeMapper.deleteByTime(LocalDateTime.now().minusMinutes(5));
    }
}

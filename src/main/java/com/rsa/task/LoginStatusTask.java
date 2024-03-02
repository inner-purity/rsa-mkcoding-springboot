package com.rsa.task;

import com.rsa.entity.User;
import com.rsa.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class LoginStatusTask {
    @Autowired
    UserMapper userMapper;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void checkLoginStatus(){
        log.info("定时任务(1min)---定期更新用户登录状态---{}",LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-120);
        List<User> userList = userMapper.filterByLastLoginTime(time);

        for (User user : userList) {
            userMapper.updateStopStatus(user);
        }
    }
}

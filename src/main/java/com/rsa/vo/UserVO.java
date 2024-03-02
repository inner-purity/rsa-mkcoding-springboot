package com.rsa.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    private String username;

    private String userLogo;

    private Integer status;

    private Integer deleting;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;

    private String updateUser;

    private String updateTime;
}

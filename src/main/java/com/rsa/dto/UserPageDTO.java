package com.rsa.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageDTO {
    private String username;

    private String userLogo;

    private Integer status;

    private Integer deleting;

    private Integer validate;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;

    private String updateUser;

    private String updateTime;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;
}

package com.rsa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//注意，如果要使用Builder,那么就必须手动或自动写入get set方法，否则就跟依赖注入一样，没有注入的途径就无法完成自动化操作
@Builder
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private Integer validate;

    private String userLogo;

    private Integer status;

    private Integer deleting;

    @TableField(exist = false)
    private String inviteKey;

    private LocalDateTime createTime;

    private LocalDateTime lastLoginTime;

    private String updateUser;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String token;

    private String email;

    @TableField(exist = false)
    private String code;

    @TableField(exist = false)
    private Integer type;
}

package com.rsa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class IpAddress {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String ipName;

    private Integer canUse;

    private String updateUser;

    private LocalDateTime updateTime;
}

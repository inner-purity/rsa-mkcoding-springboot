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
public class RequestPath {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String note;

    private String requestPath;

    private int canRequest;

    private int watchIt;

    private String updateUser;

    private LocalDateTime updateTime;
}

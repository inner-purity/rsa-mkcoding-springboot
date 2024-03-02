package com.rsa.entity;

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
public class LogException {
    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDateTime logTime;

    private Integer exceptionClassId;

    private String requestIp;

    private String requestPath;

    private String className;

    private String methodName;

    private String exceptionMessage;
}

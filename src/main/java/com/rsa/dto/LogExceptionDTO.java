package com.rsa.dto;

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
public class LogExceptionDTO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDateTime logTime;

    private String exceptionClass;

    private String requestIp;

    private String requestPath;

    private String className;

    private String methodName;

    private String exceptionMessage;
}
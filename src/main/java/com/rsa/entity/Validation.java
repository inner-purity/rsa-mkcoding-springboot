package com.rsa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Validation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String email;
    private String code;
    private Integer type;
    private Date time;
}

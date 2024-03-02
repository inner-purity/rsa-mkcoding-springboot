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
public class RsaKeyQrcodeDTO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String unicode;
    private String keyType;
    private String keyForce;
    private String publicKey;
    private String privateKey;
    private String password;
    private Integer storeUserId;
    @TableField(exist = false)
    private String username;
    private LocalDateTime storeTime;
}

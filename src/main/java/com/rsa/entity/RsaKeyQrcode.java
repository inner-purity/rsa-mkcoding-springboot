package com.rsa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RsaKeyQrcode {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String unicode;
    private String keyType;
    private String keyForce;
    private String publicKey;
    private String privateKey;
    private String password;
    private Integer storeUserId;
    private LocalDateTime storeTime;
}

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
public class RsaKeyDTO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private LocalDateTime storeTime;

    private String keyType;

    private String keyForce;

    private String note;

    private String privateKey;

    private String publicKey;

    private String privatePassword;
}

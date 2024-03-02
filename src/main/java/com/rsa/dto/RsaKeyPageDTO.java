package com.rsa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsaKeyPageDTO {
    private Integer id;

    private Integer userId;

    private LocalDateTime storeTime;

    private String keyType;

    private String keyForce;

    private String note;

    private String privateKey;

    private String publicKey;

    private String privatePassword;

    //页码
    private int pageNum;

    //每页显示记录数
    private int pageSize;
}

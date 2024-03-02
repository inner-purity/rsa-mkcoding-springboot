package com.rsa.dto;

import lombok.Data;

@Data
public class RequestPathPageDTO {
    //接口路径名
    private String requestPath;

    private String note;

    private  Integer canRequest;

    private Integer watchIt;

    private String updateUser;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;
}

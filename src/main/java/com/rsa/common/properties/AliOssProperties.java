package com.rsa.common.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rsa.alioss")
//以上是配置属性类注解  设置之后可以抓取配置文件yml中的配置属性，存储为该对象(命名时配置文件中一般写成)
@Data
public class AliOssProperties {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}

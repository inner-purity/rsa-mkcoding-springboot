package com.rsa.config;


import com.rsa.common.properties.AliOssProperties;
import com.rsa.common.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建AliOssUtil对象
 * 相关联的文件--AliOssProperties.java   AliOssUtil.java 详情跳转至对应文件查看
 */
@Configuration
@Slf4j
public class OssConfiguration {
    //配置文件中构建工厂函数，用于创建AliOssUtil对象实例并返回，所以工厂函数交付给spring框架管理要在函数上加@Bean注解
    //这样就把第三方库打包成Bean交给spring管理
    @Bean
    @ConditionalOnMissingBean
    //以上是用于设置这是个条件对象，只有在这个Bean还没有被创建时创建，
    // 如果别的组件已经用到它并创建出来，那么就不用再重复创建了,毕竟是工具类
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云文件上传工具类对象: {}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }
}

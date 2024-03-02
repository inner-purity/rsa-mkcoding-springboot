package com.rsa.config;

import com.rsa.interceptor.IpInterceptor;
import com.rsa.interceptor.JwtInterceptor;
import com.rsa.interceptor.RequestPathInterceptor;
import com.rsa.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebConfigeration extends WebMvcConfigurationSupport {
    @Autowired
    JwtInterceptor jwtInterceptor;
    @Autowired
    IpInterceptor ipInterceptor;
    @Autowired
    RequestPathInterceptor requestPathInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/users/login/**", "/users/appLogin/**", "/imserver/**", "/users/common/sendEmailCode/**", "/users/sign");
        registry.addInterceptor(requestPathInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/users/login/**", "/users/appLogin/**", "/imserver/**");
        registry.addInterceptor(ipInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/users/login/**", "/users/appLogin/**", "/imserver/**");
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("开始注册扩展消息转换器");
        //创建一个消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //创建一个对象转换器,用于将java对象序列化成json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, converter);
        //这里设置为0，因为框架自带了一些converter,为了先使用自己定义的，这里提高优先级，写0
    }
}

package com.rsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfiguration {
    //创建ServerEndpointExporter的工厂函数
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){return new ServerEndpointExporter();}
}

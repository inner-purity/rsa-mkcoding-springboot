package com.rsa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfigeration {
    @Value("${spring.qq.mail.protocol}")
    private String qqProtocol;
    @Value("${spring.qq.mail.host}")
    private String qqHost;
    @Value("${spring.qq.mail.port}")
    private int qqPort;
    @Value("${spring.qq.mail.username}")
    private String qqUsername;
    @Value("${spring.qq.mail.password}")
    private String qqPassword;

    @Value("${spring.mail163.mail.protocol}")
    private String _mail163Protocol;
    @Value("${spring.mail163.mail.host}")
    private String _mail163Host;
    @Value("${spring.mail163.mail.port}")
    private int _mail163Port;
    @Value("${spring.mail163.mail.username}")
    private String _mail163Username;
    @Value("${spring.mail163.mail.password}")
    private String _mail163Password;

    @Value("${spring.mail126.mail.protocol}")
    private String _mail126Protocol;
    @Value("${spring.mail126.mail.host}")
    private String _mail126Host;
    @Value("${spring.mail126.mail.port}")
    private int _mail126Port;
    @Value("${spring.mail126.mail.username}")
    private String _mail126Username;
    @Value("${spring.mail126.mail.password}")
    private String _mail126Password;

    @Value("${spring.qq.mail.default-encoding}")
    private String _mailDefaultEncoding;

    @Bean
    @ConfigurationProperties(prefix = "spring.qq.mail")
    public JavaMailSender qqMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setProtocol(qqProtocol);
        mailSender.setHost(qqHost);
        mailSender.setPort(qqPort);
        mailSender.setUsername(qqUsername);
        mailSender.setPassword(qqPassword);
        mailSender.setDefaultEncoding(_mailDefaultEncoding);
        return mailSender;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.mail163.mail")
    public JavaMailSender _163MailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setProtocol(_mail163Protocol);
        mailSender.setHost(_mail163Host);
        mailSender.setPort(_mail163Port);
        mailSender.setUsername(_mail163Username);
        mailSender.setPassword(_mail163Password);
        mailSender.setDefaultEncoding(_mailDefaultEncoding);
        return mailSender;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.mail126.mail")
    public JavaMailSender _126MailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setProtocol(_mail126Protocol);
        mailSender.setHost(_mail126Host);
        mailSender.setPort(_mail126Port);
        mailSender.setUsername(_mail126Username);
        mailSender.setPassword(_mail126Password);
        mailSender.setDefaultEncoding(_mailDefaultEncoding);
        return mailSender;
    }
}

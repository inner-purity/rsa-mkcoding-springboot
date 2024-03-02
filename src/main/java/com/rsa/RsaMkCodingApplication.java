package com.rsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RsaMkCodingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsaMkCodingApplication.class, args);
    }

}

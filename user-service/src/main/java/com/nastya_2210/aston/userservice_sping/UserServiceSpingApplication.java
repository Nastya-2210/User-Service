package com.nastya_2210.aston.userservice_sping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
@EnableRetry
public class UserServiceSpingApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceSpingApplication.class, args);
    }

}

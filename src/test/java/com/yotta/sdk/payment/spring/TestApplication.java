package com.yotta.sdk.payment.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.yotta.sdk.payment.spring.config")
public class TestApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}

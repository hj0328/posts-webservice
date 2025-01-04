package com.hj.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 내장 WAS 실행하여, 언제 어디서나 같은 환경에서 스프링부트 배포 가능
        SpringApplication.run(Application.class, args);
    }
}

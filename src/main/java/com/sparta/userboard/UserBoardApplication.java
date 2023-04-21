package com.sparta.userboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing      //JPA Auditing 을 활성화
@SpringBootApplication
public class UserBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserBoardApplication.class, args);
    }
}
package com.leo.checkin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author leo
 */
@EnableScheduling
@SpringBootApplication
@MapperScan("com.leo.checkin.dao")
public class CheckinApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheckinApplication.class, args);
    }

}

package com.xw;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.xw.user.mapper"})
public class SpringApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationStarter.class);
    }
}

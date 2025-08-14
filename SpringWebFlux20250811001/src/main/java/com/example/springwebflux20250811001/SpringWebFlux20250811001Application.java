package com.example.springwebflux20250811001;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springwebflux20250811001.mapper")
public class SpringWebFlux20250811001Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebFlux20250811001Application.class, args);
    }

}

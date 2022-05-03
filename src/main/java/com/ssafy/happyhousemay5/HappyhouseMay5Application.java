package com.ssafy.happyhousemay5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.ssafy.happyhousemay5.mapper")
public class HappyhouseMay5Application {

    public static void main(String[] args) {
        SpringApplication.run(HappyhouseMay5Application.class, args);
    }

}

package com.he.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"com.he"})
@MapperScan(basePackages = "com.he.eduservice.mapper")
public class CMSApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run(CMSApplication8004.class,args);

    }
}

package com.he.ustatistic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.he"})
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling
//@MapperScan(basePackages = "{com.he.ustatistic.mapper}")
public class StaticApplicationMain8008 {
    public static void main(String[] args) {
        SpringApplication.run(StaticApplicationMain8008.class,args);

    }
}

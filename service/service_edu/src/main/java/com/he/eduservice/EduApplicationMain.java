package com.he.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"com.he"})
@EnableFeignClients
@EnableDiscoveryClient
public class EduApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(EduApplicationMain.class, args);
    }
}

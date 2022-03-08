package com.he;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.he.ucenter.mapper"})
@EnableFeignClients
@EnableDiscoveryClient
public class UcenterMain {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMain.class, args);
    }
}

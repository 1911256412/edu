package com.he.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.he"})
@MapperScan(basePackages = {"com.he.order.mapper"})
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication8007.class, args);
    }
}

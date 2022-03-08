package com.he.benteng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.he"})
@EnableDiscoveryClient
public class VodApplicationMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(VodApplicationMain8004.class,args);
    }
}

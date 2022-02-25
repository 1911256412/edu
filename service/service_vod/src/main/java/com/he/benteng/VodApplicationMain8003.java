package com.he.benteng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.he"})
public class VodApplicationMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(VodApplicationMain8003.class,args);
    }
}

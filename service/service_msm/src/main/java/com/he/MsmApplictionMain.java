package com.he;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@ComponentScan(basePackages = {"com.he.service"})
public class MsmApplictionMain {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplictionMain.class, args);

    }
}

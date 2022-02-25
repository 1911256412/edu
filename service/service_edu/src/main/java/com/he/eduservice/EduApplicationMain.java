package com.he.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"com.he"})

public class EduApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(EduApplicationMain.class, args);
    }
}

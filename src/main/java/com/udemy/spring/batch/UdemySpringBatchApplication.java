package com.udemy.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
//@ComponentScan("com.udemy.spring.batch.config")
public class UdemySpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(UdemySpringBatchApplication.class, args);
    }

}

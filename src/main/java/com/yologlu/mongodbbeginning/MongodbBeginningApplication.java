package com.yologlu.mongodbbeginning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MongodbBeginningApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbBeginningApplication.class, args);
    }
}

package com.example.tyfserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class TyfServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TyfServerApplication.class, args);
    }

}

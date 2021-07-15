package com.example.tyfserver;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@EnableEncryptableProperties
@SpringBootApplication
public class TyfServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TyfServerApplication.class, args);
    }

}

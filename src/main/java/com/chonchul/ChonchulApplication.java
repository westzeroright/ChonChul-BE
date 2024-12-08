package com.chonchul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChonchulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChonchulApplication.class, args);
    }

}

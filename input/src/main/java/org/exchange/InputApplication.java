package org.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;

@EnableScheduling
@SpringBootApplication
public class InputApplication {

    public static void main(String[] args) {

        SpringApplication.run(InputApplication.class, args);
    }

}

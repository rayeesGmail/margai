package com.margai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** MARG AI API: one deployable modular monolith (DEV_SPEC §2). */
@SpringBootApplication
public class MargaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MargaiApplication.class, args);
    }
}

package com.tekcapzule.references.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapzule.references","com.tekcapzule.core.config"})
public class ReferencesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReferencesApplication.class, args);
    }
}
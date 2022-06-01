package com.rogerioreis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@SpringBootApplication
public class ServiceStatusApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(ServiceStatusApplication.class, args);
    }
}

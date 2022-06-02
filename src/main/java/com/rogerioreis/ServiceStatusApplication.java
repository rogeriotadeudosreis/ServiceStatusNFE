package com.rogerioreis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.io.IOException;

@SpringBootApplication
@EnableSpringDataWebSupport
public class ServiceStatusApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(ServiceStatusApplication.class, args);
    }
}

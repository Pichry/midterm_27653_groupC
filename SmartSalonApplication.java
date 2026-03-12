package com.smartsalon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SmartSalonApplication – entry point for the Spring Boot application.
 *
 * @SpringBootApplication is a convenience annotation that combines:
 *   @Configuration       – marks this as a Spring configuration class
 *   @EnableAutoConfiguration – enables Spring Boot's auto-configuration
 *   @ComponentScan       – scans com.smartsalon and all sub-packages
 *                          for @Component, @Service, @Repository, @Controller beans
 */
@SpringBootApplication
public class SmartSalonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSalonApplication.class, args);
    }
}
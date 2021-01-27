package com.epam.esm.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class {@code ApplicationRunner} contains method to run Spring Boot application.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@SpringBootApplication
public class ApplicationRunner {

    /**
     * The entry point of Spring Boot application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}

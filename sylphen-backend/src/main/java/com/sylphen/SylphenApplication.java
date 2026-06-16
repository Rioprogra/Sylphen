package com.sylphen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de Sylphen Backend.
 *
 * @SpringBootApplication activa automáticamente:
 *   - @Configuration    → configuración de beans
 *   - @EnableAutoConfiguration → configura Spring Boot automáticamente
 *   - @ComponentScan    → escanea todos los @Component, @Service, @Controller en com.sylphen
 *
 * Arranque: mvn spring-boot:run
 * El servidor inicia en http://localhost:8080
 */
@SpringBootApplication
public class SylphenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SylphenApplication.class, args);
    }
}

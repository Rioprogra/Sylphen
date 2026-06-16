package com.sylphen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Prueba de arranque básica.
 * Verifica que el contexto de Spring Boot carga correctamente.
 * Ejecutar con: mvn test
 */
@SpringBootTest
class SylphenApplicationTests {

    @Test
    void contextLoads() {
        // Si el contexto carga sin errores, el test pasa.
        // Es la prueba más básica y más importante.
    }
}

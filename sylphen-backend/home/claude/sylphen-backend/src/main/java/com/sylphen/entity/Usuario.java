package com.sylphen.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que mapea la tabla USUARIO.
 *
 * Columnas:
 *   id_usuario    → PK autoincremental
 *   nombre        → nombre completo del usuario
 *   usuario       → nombre de usuario único (login)
 *   correo        → correo electrónico único
 *   password      → contraseña (en Fase 2 se almacenará con hash bcrypt)
 *   rol           → 'USUARIO' o 'ADMINISTRADOR'
 *   fecha_registro→ timestamp de creación automático
 */
@Entity
@Table(name = "USUARIO")
@Data                   // genera getters, setters, equals, hashCode, toString
@NoArgsConstructor      // constructor vacío requerido por JPA
@AllArgsConstructor     // constructor con todos los campos
@Builder                // patrón builder: Usuario.builder().nombre("...").build()
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "usuario", nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    // En Fase 2: almacenar con BCryptPasswordEncoder antes de guardar
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "rol", nullable = false, length = 20)
    @Builder.Default
    private String rol = "USUARIO"; // valor por defecto: usuario común

    @Column(name = "fecha_registro", updatable = false)
    @Builder.Default
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // Callback que asigna fecha_registro automáticamente antes del primer INSERT
    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) {
            fechaRegistro = LocalDateTime.now();
        }
    }
}

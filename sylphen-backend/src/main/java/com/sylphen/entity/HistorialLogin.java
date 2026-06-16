package com.sylphen.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que mapea la tabla HISTORIAL_LOGIN.
 *
 * Registra cada LOGIN y LOGOUT del sistema.
 * El campo 'evento' distingue entre ambos.
 * Hibernate agrega la columna automáticamente al reiniciar
 * gracias a ddl-auto=update.
 */
@Entity
@Table(name = "HISTORIAL_LOGIN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login")
    private Long idLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "fecha_hora", nullable = false)
    @Builder.Default
    private LocalDateTime fechaHora = LocalDateTime.now();

    /* 'LOGIN' o 'LOGOUT'
       Hibernate añade esta columna a MySQL con ddl-auto=update */
    @Column(name = "evento", nullable = false, length = 10)
    @Builder.Default
    private String evento = "LOGIN";

    @PrePersist
    protected void onCreate() {
        if (fechaHora == null) fechaHora = LocalDateTime.now();
        if (evento    == null) evento    = "LOGIN";
    }
}

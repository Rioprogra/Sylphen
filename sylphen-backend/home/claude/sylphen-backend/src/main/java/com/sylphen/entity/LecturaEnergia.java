package com.sylphen.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA que mapea la tabla LECTURA_ENERGIA.
 *
 * Almacena cada lectura enviada por el ESP32 desde el sensor INA226.
 * Un registro = una medición en un momento dado.
 *
 * El ESP32 envía lecturas a POST /api/lectura y se almacenan en SQL.
 *
 * Columnas:
 *   id_lectura        → PK autoincremental
 *   fecha_hora        → timestamp de la medición
 *   voltaje           → voltaje de la batería en V (ej: 3.89)
 *   corriente         → corriente de carga en A (ej: 1.24)
 *   potencia          → potencia calculada V×I en W (ej: 4.83)
 *   bateria_porcentaje→ nivel estimado de la batería 18650 (0-100)
 */
@Entity
@Table(
    name = "LECTURA_ENERGIA",
    indexes = {
        // Índice en fecha_hora para acelerar consultas de historial
        @Index(name = "idx_lectura_fecha", columnList = "fecha_hora")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturaEnergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lectura")
    private Long idLectura;

    @Column(name = "fecha_hora", nullable = false)
    @Builder.Default
    private LocalDateTime fechaHora = LocalDateTime.now();

    // BigDecimal para evitar errores de punto flotante en valores eléctricos
    @Column(name = "voltaje", nullable = false, precision = 5, scale = 2)
    private BigDecimal voltaje;

    @Column(name = "corriente", nullable = false, precision = 5, scale = 2)
    private BigDecimal corriente;

    @Column(name = "potencia", nullable = false, precision = 6, scale = 2)
    private BigDecimal potencia;

    // TINYINT(3) → valores entre 0 y 100
    @Column(name = "bateria_porcentaje", nullable = false)
    private Integer bateriaPorcentaje;

    @PrePersist
    protected void onCreate() {
        if (fechaHora == null) {
            fechaHora = LocalDateTime.now();
        }
    }
}

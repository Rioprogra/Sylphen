package com.sylphen.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que mapea la tabla DISPOSITIVO.
 *
 * Registros fijos (se insertan una sola vez con data.sql en Fase 2):
 *   1. Arduino Uno    - microcontrolador
 *   2. ESP32          - comunicacion
 *   3. INA226         - sensor
 *   4. Batería 18650  - almacenamiento
 *
 * El estado se actualiza cada vez que el ESP32 reporta correctamente.
 *
 * Columnas:
 *   id_dispositivo → PK autoincremental
 *   nombre         → nombre del componente físico
 *   tipo           → categoría del dispositivo
 *   estado         → 'ONLINE' | 'OFFLINE' | 'ERROR'
 *   ultima_lectura → timestamp de la última comunicación exitosa
 */
@Entity
@Table(name = "DISPOSITIVO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dispositivo")
    private Long idDispositivo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    // 'ONLINE', 'OFFLINE', 'ERROR'
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "OFFLINE";

    @Column(name = "ultima_lectura")
    private LocalDateTime ultimaLectura;
}

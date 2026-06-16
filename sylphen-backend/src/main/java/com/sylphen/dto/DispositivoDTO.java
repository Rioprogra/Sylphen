package com.sylphen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO para GET /api/dispositivos.
 * Lista el estado de cada componente físico del sistema.
 * Usado por la pantalla P6 — Dispositivos del frontend.
 *
 * Respuesta esperada (array):
 * [
 *   { "nombre": "Arduino Uno",   "tipo": "microcontrolador", "estado": "ONLINE",  "ultimaLectura": "..." },
 *   { "nombre": "ESP32",          "tipo": "comunicacion",     "estado": "ONLINE",  "ultimaLectura": "..." },
 *   { "nombre": "INA226",         "tipo": "sensor",           "estado": "ONLINE",  "ultimaLectura": "..." },
 *   { "nombre": "Batería 18650",  "tipo": "almacenamiento",   "estado": "ONLINE",  "ultimaLectura": "..." }
 * ]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DispositivoDTO {

    private String nombre;
    private String tipo;
    private String estado;           // "ONLINE" | "OFFLINE" | "ERROR"
    private LocalDateTime ultimaLectura;
    private String detalle;          // info adicional para mostrar en la card
}

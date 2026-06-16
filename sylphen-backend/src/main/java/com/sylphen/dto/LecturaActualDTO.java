package com.sylphen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para GET /api/lectura/actual.
 * Devuelve la lectura más reciente del sensor INA226.
 * Usado por el Dashboard (P3) y la pantalla de Generación (P4).
 *
 * Respuesta esperada:
 * {
 *   "voltaje": 3.89,
 *   "corriente": 1.24,
 *   "potencia": 4.83,
 *   "bateria_porcentaje": 78,
 *   "bateria_estado": "cargando",
 *   "bateria_tiempo_min": 84,
 *   "esp32_online": true,
 *   "lectura_seg": 8,
 *   "var_energia_pct": 12,
 *   "fecha_hora": "2026-06-10T14:32:00"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturaActualDTO {

    private BigDecimal voltaje;
    private BigDecimal corriente;
    private BigDecimal potencia;
    private Integer bateriaPorcentaje;
    private String bateriaEstado;
    private Integer bateriaTiempoMin;
    private Boolean esp32Online;
    private Integer lecturaSeg;
    private Integer varEnergiaPct;
    private BigDecimal energiaHoyKwh;
    private LocalDateTime fechaHora;
}

package com.sylphen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para GET /api/lecturas?from=&to= (historial).
 * Usado por la pantalla P5 — Historial del frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LecturaHistorialDTO {

    private Long idLectura;
    private LocalDateTime fechaHora;
    private BigDecimal voltaje;
    private BigDecimal corriente;
    private BigDecimal potencia;
    private Integer bateriaPorcentaje;
}

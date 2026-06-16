package com.sylphen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para GET /api/estado (endpoint público, sin autenticación).
 * Usado por la pantalla P1 — Inicio público del frontend.
 *
 * Respuesta esperada:
 * {
 *   "bateria_porcentaje": 78,
 *   "sistema_estado": "activo"
 * }
 *
 * sistema_estado: "activo" | "sin_conexion" | "critico"
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDTO {

    private Integer bateriaPorcentaje;
    private String sistemaEstado;
}

package com.sylphen.service;

import com.sylphen.dto.EstadoDTO;
import com.sylphen.repository.LecturaEnergiaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servicio para GET /api/estado (endpoint público P1).
 * Lee la última lectura de MySQL para mostrar batería real.
 * Fallback a valores simulados si la tabla está vacía.
 */
@Service
public class EstadoService {

    private final LecturaEnergiaRepository repo;

    public EstadoService(LecturaEnergiaRepository repo) {
        this.repo = repo;
    }

    public EstadoDTO obtenerEstado() {
        return repo.findTopByOrderByFechaHoraDesc()
                .map(l -> {
                    boolean online = l.getFechaHora()
                            .isAfter(LocalDateTime.now().minusSeconds(30));
                    int pct = l.getBateriaPorcentaje() != null ? l.getBateriaPorcentaje() : 0;
                    String estado = !online ? "sin_conexion"
                            : pct <= 15 ? "critico"
                            : "activo";
                    return EstadoDTO.builder()
                            .bateriaPorcentaje(pct)
                            .sistemaEstado(estado)
                            .build();
                })
                .orElse(EstadoDTO.builder()          // tabla vacía → sin datos reales
                        .bateriaPorcentaje(null)
                        .sistemaEstado("sin_conexion")
                        .build());
    }
}

package com.sylphen.service;

import com.sylphen.dto.EstadoDTO;
import com.sylphen.repository.LecturaEnergiaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servicio para el endpoint público GET /api/estado.
 * Obtiene el estado desde la tabla LECTURA_ENERGIA.
 */
@Service
public class EstadoService {

    private final LecturaEnergiaRepository lecturaEnergiaRepository;

    public EstadoService(LecturaEnergiaRepository lecturaEnergiaRepository) {
        this.lecturaEnergiaRepository = lecturaEnergiaRepository;
    }

    public EstadoDTO obtenerEstado() {
        return lecturaEnergiaRepository.findTopByOrderByFechaHoraDesc()
                .map(lectura -> {
                    boolean online = lectura.getFechaHora().isAfter(LocalDateTime.now().minusSeconds(30));
                    String estado = !online ? "sin_conexion"
                            : lectura.getBateriaPorcentaje() <= 15 ? "critico"
                            : "activo";

                    return EstadoDTO.builder()
                            .bateriaPorcentaje(lectura.getBateriaPorcentaje())
                            .sistemaEstado(estado)
                            .build();
                })
                .orElseGet(() -> EstadoDTO.builder()
                        .bateriaPorcentaje(0)
                        .sistemaEstado("sin_conexion")
                        .build());
    }
}

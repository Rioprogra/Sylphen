package com.sylphen.service;

import com.sylphen.dto.LecturaActualDTO;
import com.sylphen.dto.LecturaHistorialDTO;
import com.sylphen.entity.LecturaEnergia;
import com.sylphen.repository.LecturaEnergiaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Sin datos simulados.
 * Todo viene de MySQL (tabla lectura_energia).
 * Si está vacía → devuelve estado offline con valores null.
 */
@Service
public class LecturaService {

    private final LecturaEnergiaRepository repo;

    public LecturaService(LecturaEnergiaRepository repo) {
        this.repo = repo;
    }

    /* ============================================================
       GET /api/lectura/actual — última fila de lectura_energia
       Si tabla vacía → offline (todos los campos null)
       ============================================================ */
    public LecturaActualDTO obtenerLecturaActual() {
        return repo.findTopByOrderByFechaHoraDesc()
                .map(this::toActualDTO)
                .orElse(LecturaActualDTO.builder()
                        .esp32Online(false)
                        .fechaHora(LocalDateTime.now())
                        .build());
    }

    /* ============================================================
       GET /api/lecturas — registros reales de lectura_energia
       Si tabla vacía → lista vacía
       ============================================================ */
    public List<LecturaHistorialDTO> obtenerHistorial(LocalDateTime desde, LocalDateTime hasta) {
        return repo.findByFechaHoraBetweenOrderByFechaHoraDesc(desde, hasta)
                .stream()
                .map(this::toHistorialDTO)
                .toList();
    }

    /* ============================================================
       POST /api/lectura — ESP32 inserta aquí
       ============================================================ */
    public LecturaEnergia guardarLectura(LecturaActualDTO dto) {
        LecturaEnergia nueva = LecturaEnergia.builder()
                .voltaje(dto.getVoltaje())
                .corriente(dto.getCorriente())
                .potencia(dto.getPotencia())
                .bateriaPorcentaje(dto.getBateriaPorcentaje())
                .fechaHora(LocalDateTime.now())
                .build();
        return repo.save(nueva);
    }

    /* ============================================================
       Conversores entidad → DTO
       ============================================================ */
    private LecturaActualDTO toActualDTO(LecturaEnergia l) {
        int pct = l.getBateriaPorcentaje() != null ? l.getBateriaPorcentaje() : 0;
        long seg = ChronoUnit.SECONDS.between(l.getFechaHora(), LocalDateTime.now());

        return LecturaActualDTO.builder()
                .voltaje(l.getVoltaje())
                .corriente(l.getCorriente())
                .potencia(l.getPotencia())
                .bateriaPorcentaje(pct)
                .bateriaEstado(estadoBateria(pct))
                .bateriaTiempoMin((100 - pct) * 5)
                .esp32Online(seg < 30)
                .lecturaSeg((int) Math.min(seg, 999))
                .varEnergiaPct(0)
                .energiaHoyKwh(BigDecimal.valueOf(0.00))
                .fechaHora(l.getFechaHora())
                .build();
    }

    private LecturaHistorialDTO toHistorialDTO(LecturaEnergia l) {
        return LecturaHistorialDTO.builder()
                .idLectura(l.getIdLectura())
                .fechaHora(l.getFechaHora())
                .voltaje(l.getVoltaje())
                .corriente(l.getCorriente())
                .potencia(l.getPotencia())
                .bateriaPorcentaje(l.getBateriaPorcentaje())
                .build();
    }

    private String estadoBateria(int pct) {
        if (pct >= 100) return "completa";
        if (pct >= 20)  return "cargando";
        if (pct >= 10)  return "critica";
        return "descargando";
    }
}

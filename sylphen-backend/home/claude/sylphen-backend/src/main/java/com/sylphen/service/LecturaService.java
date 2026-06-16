package com.sylphen.service;

import com.sylphen.dto.LecturaActualDTO;
import com.sylphen.dto.LecturaHistorialDTO;
import com.sylphen.entity.LecturaEnergia;
import com.sylphen.repository.LecturaEnergiaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para lecturas del sensor INA226.
 * Consulta directamente la tabla LECTURA_ENERGIA en la base de datos.
 */
@Service
public class LecturaService {

    private final LecturaEnergiaRepository lecturaEnergiaRepository;

    public LecturaService(LecturaEnergiaRepository lecturaEnergiaRepository) {
        this.lecturaEnergiaRepository = lecturaEnergiaRepository;
    }

    public LecturaActualDTO obtenerLecturaActual() {
        return lecturaEnergiaRepository.findTopByOrderByFechaHoraDesc()
                .map(this::toLecturaActualDto)
                .orElseThrow(() -> new RuntimeException("Sin lecturas disponibles"));
    }

    public List<LecturaHistorialDTO> obtenerHistorial(LocalDateTime desde, LocalDateTime hasta) {
        return lecturaEnergiaRepository.findByFechaHoraBetweenOrderByFechaHoraDesc(desde, hasta)
                .stream()
                .map(this::toHistorialDto)
                .toList();
    }

    private LecturaActualDTO toLecturaActualDto(LecturaEnergia lectura) {
        boolean esp32Online = lectura.getFechaHora().isAfter(LocalDateTime.now().minusSeconds(30));
        int lecturaSeg = (int) Math.max(0, Duration.between(lectura.getFechaHora(), LocalDateTime.now()).getSeconds());

        return LecturaActualDTO.builder()
                .voltaje(lectura.getVoltaje())
                .corriente(lectura.getCorriente())
                .potencia(lectura.getPotencia())
                .bateriaPorcentaje(lectura.getBateriaPorcentaje())
                .bateriaEstado(calcularEstadoBateria(lectura.getBateriaPorcentaje()))
                .bateriaTiempoMin(Math.max(0, lectura.getBateriaPorcentaje() * 2))
                .esp32Online(esp32Online)
                .lecturaSeg(lecturaSeg)
                .varEnergiaPct(0)
                .energiaHoyKwh(BigDecimal.ZERO)
                .fechaHora(lectura.getFechaHora())
                .build();
    }

    private LecturaHistorialDTO toHistorialDto(LecturaEnergia lectura) {
        return LecturaHistorialDTO.builder()
                .idLectura(lectura.getIdLectura())
                .fechaHora(lectura.getFechaHora())
                .voltaje(lectura.getVoltaje())
                .corriente(lectura.getCorriente())
                .potencia(lectura.getPotencia())
                .bateriaPorcentaje(lectura.getBateriaPorcentaje())
                .build();
    }

    private String calcularEstadoBateria(int porcentaje) {
        if (porcentaje <= 15) {
            return "critico";
        }
        if (porcentaje < 30) {
            return "bajo";
        }
        return "cargando";
    }
}

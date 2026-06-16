package com.sylphen.service;

import com.sylphen.dto.DispositivoDTO;
import com.sylphen.repository.DispositivoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Lee los 4 dispositivos físicos directamente de la tabla DISPOSITIVO en MySQL.
 * El ESP32 actualizará estado y ultima_lectura vía PUT /api/dispositivos/{id}.
 */
@Service
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    public DispositivoService(DispositivoRepository dispositivoRepository) {
        this.dispositivoRepository = dispositivoRepository;
    }

    public List<DispositivoDTO> obtenerDispositivos() {
        return dispositivoRepository.findAll()
                .stream()
                .map(d -> DispositivoDTO.builder()
                        .nombre(d.getNombre())
                        .tipo(d.getTipo())
                        .estado(d.getEstado())
                        .ultimaLectura(d.getUltimaLectura())
                        .detalle(d.getTipo())
                        .build())
                .toList();
    }

    public void actualizarEstado(String nombre, String estado) {
        dispositivoRepository.findByNombre(nombre).ifPresent(d -> {
            d.setEstado(estado);
            d.setUltimaLectura(LocalDateTime.now());
            dispositivoRepository.save(d);
        });
    }
}

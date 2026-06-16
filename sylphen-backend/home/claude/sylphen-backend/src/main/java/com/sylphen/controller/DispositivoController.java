package com.sylphen.controller;

import com.sylphen.dto.DispositivoDTO;
import com.sylphen.service.DispositivoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para el estado de los dispositivos físicos del sistema.
 *
 * Endpoints:
 *   GET /api/dispositivos        → lista los 4 componentes (P6 Dispositivos)
 *   PUT /api/dispositivos/{id}   → actualiza el estado de un componente (Fase 2)
 */
@RestController
@RequestMapping("/api")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    /**
     * GET /api/dispositivos
     *
     * Respuesta:
     * [
     *   { "nombre": "Arduino Uno",  "estado": "ONLINE", ... },
     *   { "nombre": "ESP32",         "estado": "ONLINE", ... },
     *   { "nombre": "INA226",        "estado": "ONLINE", ... },
     *   { "nombre": "Batería 18650", "estado": "ONLINE", ... }
     * ]
     */
    @GetMapping("/dispositivos")
    public ResponseEntity<List<DispositivoDTO>> obtenerDispositivos() {
        List<DispositivoDTO> dispositivos = dispositivoService.obtenerDispositivos();
        return ResponseEntity.ok(dispositivos);
    }

    /**
     * PUT /api/dispositivos/{id}
     * Actualiza el estado de un dispositivo.
     * En Fase 2: el ESP32 actualizará su propio estado periódicamente.
     * Fase 1: responde 200 sin hacer nada.
     */
    @PutMapping("/dispositivos/{id}")
    public ResponseEntity<String> actualizarDispositivo(
            @PathVariable Long id,
            @RequestBody DispositivoDTO dto) {
        // Fase 2: dispositivoService.actualizarEstado(id, dto)
        return ResponseEntity.ok("Estado actualizado");
    }
}

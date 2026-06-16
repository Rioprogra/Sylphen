package com.sylphen.controller;

import com.sylphen.dto.LecturaActualDTO;
import com.sylphen.dto.LecturaHistorialDTO;
import com.sylphen.service.LecturaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para endpoints de lecturas del sensor INA226.
 *
 * Endpoints:
 *   GET /api/lectura/actual      → última lectura (P3 Dashboard, P4 Generación)
 *   GET /api/lecturas            → historial por rango de fechas (P5 Historial)
 *   POST /api/lectura            → recibir nueva lectura del ESP32 (Fase 2)
 */
@RestController
@RequestMapping("/api")
public class LecturaController {

    private final LecturaService lecturaService;

    public LecturaController(LecturaService lecturaService) {
        this.lecturaService = lecturaService;
    }

    /**
     * GET /api/lectura/actual
     * Devuelve la lectura más reciente. Llamado por el frontend cada 10s.
     *
     * Respuesta:
     * {
     *   "voltaje": 3.89,
     *   "corriente": 1.24,
     *   "potencia": 4.83,
     *   "bateria_porcentaje": 78,
     *   "bateria_estado": "cargando",
     *   "esp32_online": true,
     *   ...
     * }
     */
    @GetMapping("/lectura/actual")
    public ResponseEntity<LecturaActualDTO> obtenerLecturaActual() {
        LecturaActualDTO lectura = lecturaService.obtenerLecturaActual();
        return ResponseEntity.ok(lectura);
    }

    /**
     * GET /api/lecturas?from=2026-06-01T00:00:00&to=2026-06-09T23:59:59
     * Historial de lecturas en un rango. Usado por P5 — Historial.
     *
     * Parámetros opcionales: si no se envían, devuelve las últimas 24h.
     */
    @GetMapping("/lecturas")
    public ResponseEntity<List<LecturaHistorialDTO>> obtenerHistorial(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to) {

        // Si no se envían parámetros: últimas 24 horas
        LocalDateTime desde = (from != null) ? from : LocalDateTime.now().minusDays(1);
        LocalDateTime hasta = (to   != null) ? to   : LocalDateTime.now();

        List<LecturaHistorialDTO> historial = lecturaService.obtenerHistorial(desde, hasta);
        return ResponseEntity.ok(historial);
    }

    /**
     * POST /api/lectura
     * El ESP32 enviará su lectura con este endpoint en Fase 2.
     * Fase 1: responde 201 Created sin guardar nada.
     *
     * Body esperado:
     * { "voltaje": 3.89, "corriente": 1.24, "potencia": 4.83, "bateria_porcentaje": 78 }
     */
    @PostMapping("/lectura")
    public ResponseEntity<String> recibirLectura(@RequestBody LecturaActualDTO lecturaDTO) {
        // Fase 1: solo confirmar recepción
        // Fase 2: lecturaService.guardarLectura(lecturaDTO)
        return ResponseEntity.status(201).body("Lectura recibida");
    }
}

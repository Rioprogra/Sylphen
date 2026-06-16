package com.sylphen.controller;

import com.sylphen.dto.LecturaActualDTO;
import com.sylphen.dto.LecturaHistorialDTO;
import com.sylphen.service.LecturaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LecturaController {

    private final LecturaService lecturaService;

    public LecturaController(LecturaService lecturaService) {
        this.lecturaService = lecturaService;
    }

    /** GET /api/lectura/actual — última lectura de MySQL (o simulada si vacía) */
    @GetMapping("/lectura/actual")
    public ResponseEntity<LecturaActualDTO> obtenerLecturaActual() {
        return ResponseEntity.ok(lecturaService.obtenerLecturaActual());
    }

    /** GET /api/lecturas — historial de MySQL (o simulado si vacío) */
    @GetMapping("/lecturas")
    public ResponseEntity<List<LecturaHistorialDTO>> obtenerHistorial(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        LocalDateTime desde = (from != null) ? from : LocalDateTime.now().minusDays(30);
        LocalDateTime hasta = (to   != null) ? to   : LocalDateTime.now();
        return ResponseEntity.ok(lecturaService.obtenerHistorial(desde, hasta));
    }

    /**
     * POST /api/lectura — el ESP32 envía sus datos aquí.
     * Guarda en tabla lectura_energia de MySQL.
     *
     * Body: { "voltaje":3.89, "corriente":1.24, "potencia":4.83, "bateriaPorcentaje":78 }
     */
    @PostMapping("/lectura")
    public ResponseEntity<String> recibirLectura(@RequestBody LecturaActualDTO dto) {
        lecturaService.guardarLectura(dto);
        return ResponseEntity.status(201).body("Lectura guardada en MySQL");
    }
}

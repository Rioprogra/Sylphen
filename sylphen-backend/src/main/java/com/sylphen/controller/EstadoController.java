package com.sylphen.controller;

import com.sylphen.dto.EstadoDTO;
import com.sylphen.service.EstadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para GET /api/estado.
 *
 * Endpoint PÚBLICO: no requiere autenticación.
 * Lo consume la pantalla P1 — Inicio público del frontend
 * para mostrar el porcentaje de batería antes del login.
 */
@RestController
@RequestMapping("/api")
public class EstadoController {

    private final EstadoService estadoService;

    // Inyección por constructor (recomendada sobre @Autowired)
    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    /**
     * GET /api/estado
     *
     * Respuesta:
     * {
     *   "bateria_porcentaje": 78,
     *   "sistema_estado": "activo"
     * }
     */
    @GetMapping("/estado")
    public ResponseEntity<EstadoDTO> obtenerEstado() {
        EstadoDTO estado = estadoService.obtenerEstado();
        return ResponseEntity.ok(estado);
    }
}

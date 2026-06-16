package com.sylphen.controller;

import com.sylphen.dto.AuthLoginDTO;
import com.sylphen.dto.AuthRegistroDTO;
import com.sylphen.dto.AuthResponseDTO;
import com.sylphen.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación.
 *
 * POST /api/auth/registro  → registra usuario en MySQL
 * POST /api/auth/login     → verifica credenciales + inserta HISTORIAL_LOGIN
 * POST /api/auth/logout    → inserta LOGOUT en HISTORIAL_LOGIN
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /** Registrar nuevo usuario */
    @PostMapping("/registro")
    public ResponseEntity<AuthResponseDTO> registro(@RequestBody AuthRegistroDTO dto) {
        AuthResponseDTO res = authService.registro(dto);
        return res.getExito()
                ? ResponseEntity.status(201).body(res)
                : ResponseEntity.badRequest().body(res);
    }

    /** Iniciar sesión */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthLoginDTO dto) {
        AuthResponseDTO res = authService.login(dto);
        return res.getExito()
                ? ResponseEntity.ok(res)
                : ResponseEntity.status(401).body(res);
    }

    /**
     * Cerrar sesión.
     * Body: { "sessionToken": "uuid-del-token" }
     * Registra LOGOUT en HISTORIAL_LOGIN.
     */
    @PostMapping("/logout")
    public ResponseEntity<AuthResponseDTO> logout(@RequestBody java.util.Map<String, String> body) {
        String token = body.getOrDefault("sessionToken", "");
        AuthResponseDTO res = authService.logout(token);
        return ResponseEntity.ok(res);
    }
}

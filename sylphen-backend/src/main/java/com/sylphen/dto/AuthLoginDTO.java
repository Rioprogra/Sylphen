package com.sylphen.dto;

import lombok.Data;

/**
 * DTO de entrada para POST /api/auth/login.
 * Recibe las credenciales del formulario de inicio de sesión.
 */
@Data
public class AuthLoginDTO {
    private String usuario;
    private String password;
}

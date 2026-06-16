package com.sylphen.dto;

import lombok.Data;

/**
 * DTO de entrada para POST /api/auth/registro.
 * Recibe los datos del formulario de registro de nuevo usuario.
 */
@Data
public class AuthRegistroDTO {
    private String nombre;
    private String usuario;
    private String correo;
    private String password;
}

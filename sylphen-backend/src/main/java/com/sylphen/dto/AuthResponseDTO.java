package com.sylphen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para login y registro.
 * sessionToken: token simple UUID para identificar la sesión activa.
 * (Sin JWT — se implementará en Fase 3)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private Boolean exito;
    private String  mensaje;
    private String  nombre;
    private String  usuario;
    private String  rol;
    private String  sessionToken; // UUID de sesión activa, null si fallo
}

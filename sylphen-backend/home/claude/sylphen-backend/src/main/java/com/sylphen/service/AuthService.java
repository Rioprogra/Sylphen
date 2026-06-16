package com.sylphen.service;

import com.sylphen.dto.AuthLoginDTO;
import com.sylphen.dto.AuthRegistroDTO;
import com.sylphen.dto.AuthResponseDTO;
import com.sylphen.entity.HistorialLogin;
import com.sylphen.entity.Usuario;
import com.sylphen.repository.HistorialLoginRepository;
import com.sylphen.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Servicio de autenticación real.
 *
 * - Registro  : guarda en tabla USUARIO con contraseña BCrypt
 * - Login     : verifica contra MySQL + registra en HISTORIAL_LOGIN
 * - Logout    : registra cierre de sesión en HISTORIAL_LOGIN
 * - Sesiones  : mapa en memoria UUID → id_usuario (sin JWT por ahora)
 */
@Service
public class AuthService {

    private final UsuarioRepository       usuarioRepo;
    private final HistorialLoginRepository historialRepo;
    private final BCryptPasswordEncoder    encoder;

    /** Sesiones activas: token UUID → id_usuario */
    private final Map<String, Long> sesionesActivas = new ConcurrentHashMap<>();

    public AuthService(UsuarioRepository u,
                       HistorialLoginRepository h,
                       BCryptPasswordEncoder e) {
        this.usuarioRepo   = u;
        this.historialRepo = h;
        this.encoder       = e;
    }

    /* ============================================================
       REGISTRO — inserta en tabla USUARIO de MySQL
       ============================================================ */
    public AuthResponseDTO registro(AuthRegistroDTO dto) {

        // 1. Validar campos obligatorios
        if (blank(dto.getNombre()) || blank(dto.getUsuario()) || blank(dto.getPassword())) {
            return error("Nombre, usuario y contraseña son obligatorios");
        }

        // 2. Verificar que el usuario no exista ya
        if (usuarioRepo.existsByUsuario(dto.getUsuario())) {
            return error("El nombre de usuario '" + dto.getUsuario() + "' ya está en uso");
        }

        // 3. Verificar correo único (si se proporcionó)
        String correo = blank(dto.getCorreo())
                ? dto.getUsuario() + "@sylphen.com"
                : dto.getCorreo();
        if (usuarioRepo.existsByCorreo(correo)) {
            return error("El correo ya está registrado");
        }

        // 4. Guardar usuario con contraseña hasheada en MySQL
        Usuario nuevo = Usuario.builder()
                .nombre(dto.getNombre())
                .usuario(dto.getUsuario())
                .correo(correo)
                .password(encoder.encode(dto.getPassword())) // BCrypt hash
                .rol("USUARIO")
                .build();

        usuarioRepo.save(nuevo);

        return AuthResponseDTO.builder()
                .exito(true)
                .mensaje("Usuario registrado correctamente. Ya puedes iniciar sesión.")
                .build();
    }

    /* ============================================================
       LOGIN — verifica contra MySQL e inserta en HISTORIAL_LOGIN
       ============================================================ */
    public AuthResponseDTO login(AuthLoginDTO dto) {

        // 1. Validar campos
        if (blank(dto.getUsuario()) || blank(dto.getPassword())) {
            return error("Usuario y contraseña son obligatorios");
        }

        // 2. Buscar usuario en MySQL
        Optional<Usuario> opt = usuarioRepo.findByUsuario(dto.getUsuario());
        if (opt.isEmpty()) {
            return error("Usuario no encontrado");
        }
        Usuario u = opt.get();

        // 3. Verificar contraseña con BCrypt
        if (!encoder.matches(dto.getPassword(), u.getPassword())) {
            return error("Contraseña incorrecta");
        }

        // 4. Generar token de sesión simple (UUID)
        String token = UUID.randomUUID().toString();
        sesionesActivas.put(token, u.getIdUsuario());

        // 5. Registrar LOGIN en tabla HISTORIAL_LOGIN
        historialRepo.save(HistorialLogin.builder()
                .usuario(u)
                .evento("LOGIN")
                .build());

        return AuthResponseDTO.builder()
                .exito(true)
                .mensaje("Sesión iniciada correctamente")
                .nombre(u.getNombre())
                .usuario(u.getUsuario())
                .rol(u.getRol())
                .sessionToken(token)
                .build();
    }

    /* ============================================================
       LOGOUT — inserta LOGOUT en HISTORIAL_LOGIN
       ============================================================ */
    public AuthResponseDTO logout(String sessionToken) {

        Long idUsuario = sesionesActivas.remove(sessionToken);

        if (idUsuario == null) {
            // Token no encontrado → sesión ya expiró o era inválido
            return AuthResponseDTO.builder()
                    .exito(true)
                    .mensaje("Sesión cerrada")
                    .build();
        }

        // Registrar LOGOUT en HISTORIAL_LOGIN
        usuarioRepo.findById(idUsuario).ifPresent(u ->
            historialRepo.save(HistorialLogin.builder()
                    .usuario(u)
                    .evento("LOGOUT")
                    .build())
        );

        return AuthResponseDTO.builder()
                .exito(true)
                .mensaje("Sesión cerrada correctamente")
                .build();
    }

    /* ============================================================
       Helper
       ============================================================ */
    private boolean blank(String s) {
        return s == null || s.isBlank();
    }

    private AuthResponseDTO error(String msg) {
        return AuthResponseDTO.builder().exito(false).mensaje(msg).build();
    }
}

package com.sylphen.repository;

import com.sylphen.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio de Usuario.
 *
 * JpaRepository<Usuario, Long> provee automáticamente:
 *   save(), findById(), findAll(), deleteById(), count(), etc.
 *
 * Los métodos personalizados usan convención de nombres de Spring Data:
 *   findBy{Campo}    → SELECT * FROM usuario WHERE campo = ?
 *   existsBy{Campo}  → SELECT COUNT(*) > 0 FROM usuario WHERE campo = ?
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar por nombre de usuario (para login)
    Optional<Usuario> findByUsuario(String usuario);

    // Verificar si un usuario o correo ya existe (para registro)
    boolean existsByUsuario(String usuario);
    boolean existsByCorreo(String correo);
}

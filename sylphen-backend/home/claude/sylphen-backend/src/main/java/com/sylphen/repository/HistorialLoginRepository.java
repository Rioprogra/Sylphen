package com.sylphen.repository;

import com.sylphen.entity.HistorialLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio de HistorialLogin.
 * Permite consultar los últimos accesos de un usuario específico.
 */
@Repository
public interface HistorialLoginRepository extends JpaRepository<HistorialLogin, Long> {

    // Obtener el historial de un usuario ordenado por fecha descendente
    List<HistorialLogin> findByUsuarioIdUsuarioOrderByFechaHoraDesc(Long idUsuario);
}

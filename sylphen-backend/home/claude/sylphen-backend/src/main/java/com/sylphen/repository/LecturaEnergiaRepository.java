package com.sylphen.repository;

import com.sylphen.entity.LecturaEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de LecturaEnergia.
 * Expone consultas SQL para leer lecturas desde MySQL.
 */
@Repository
public interface LecturaEnergiaRepository extends JpaRepository<LecturaEnergia, Long> {

    // Última lectura registrada (para GET /api/lectura/actual)
    Optional<LecturaEnergia> findTopByOrderByFechaHoraDesc();

    // Lecturas en un rango de fechas (para GET /api/lecturas)
    List<LecturaEnergia> findByFechaHoraBetweenOrderByFechaHoraDesc(
        LocalDateTime desde,
        LocalDateTime hasta
    );
}

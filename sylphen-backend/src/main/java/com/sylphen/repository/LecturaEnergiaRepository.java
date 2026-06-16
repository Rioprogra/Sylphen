package com.sylphen.repository;

import com.sylphen.entity.LecturaEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de LecturaEnergia.
 * Fase 1: los servicios no llaman estos métodos (devuelven datos simulados).
 * Fase 2: se conectarán a MySQL real.
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

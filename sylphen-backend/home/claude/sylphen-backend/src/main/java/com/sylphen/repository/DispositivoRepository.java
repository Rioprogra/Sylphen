package com.sylphen.repository;

import com.sylphen.entity.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio de Dispositivo.
 * La tabla tiene 4 registros fijos (no se insertan ni eliminan dinámicamente).
 * Solo se actualizan: estado y ultima_lectura.
 */
@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    // Buscar todos los dispositivos con un estado específico
    List<Dispositivo> findByEstado(String estado);

    // Buscar por nombre (ej: "ESP32")
    java.util.Optional<Dispositivo> findByNombre(String nombre);
}

package com.tarea6.crudh2entregable.repository;

import com.tarea6.crudh2entregable.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

    List<Videojuego> findByPlataforma(String plataforma);
    List<Videojuego> findByPuntuacion(double puntuacion);
}

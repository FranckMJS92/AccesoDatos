package com.tarea6.crudh2entregable.service;

import com.tarea6.crudh2entregable.model.Videojuego;

import java.util.List;

public interface VideojuegoService {

    List<Videojuego> findAll();

    Videojuego findById(long id);

    Videojuego create(Videojuego v);

    Videojuego update(long id, Videojuego v);

    void delete(long id);

    List<Videojuego> findByPlataforma(String plataforma);

    List<Videojuego> findByPuntuacion(double puntuacion);
}

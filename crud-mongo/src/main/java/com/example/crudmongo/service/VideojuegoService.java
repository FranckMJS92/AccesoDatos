package com.example.crudmongo.service;

import com.example.crudmongo.model.Videojuego;

import java.util.List;

public interface VideojuegoService {

    List<Videojuego> findAll();
    Videojuego findById(String id);
    Videojuego create(Videojuego v);
    Videojuego update(String id, Videojuego v);
    void delete(String id);

    //Extra consultas
    List<Videojuego> findByPlataforma(String plataforma);
    List<Videojuego> findByGenero(String genero);
}

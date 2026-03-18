package com.tarea7.catalogo_mongo.service;

import com.tarea7.catalogo_mongo.model.Libro;

import java.util.List;

public interface LibroService {

    List<Libro> findAll();

    Libro findById(String id);

    Libro create(Libro l);

    Libro update(String id, Libro l);

    void delete(String id);

    List<Libro> findByAutor(String autor);

    List<Libro> findByTitulo(String titulo);

    List<Libro> findByGenero(String genero);

}

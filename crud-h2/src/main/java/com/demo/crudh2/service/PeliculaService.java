package com.demo.crudh2.service;

import com.demo.crudh2.model.Pelicula;

import java.util.List;

public interface PeliculaService {

    List<Pelicula> findAll();

    Pelicula findById(long id);

    Pelicula create(Pelicula p);

    Pelicula update(long id, Pelicula p);

    void delete(long id);
}

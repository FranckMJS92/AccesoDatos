package com.dam.demo.service;

import com.dam.demo.model.Pelicula;
import java.util.List;

public interface PeliculaService {

    List<Pelicula> findAll();

    Pelicula findById(Long id);

    Pelicula create(Pelicula p);

    Pelicula update(Long id, Pelicula p);

    void delete(Long id);

}

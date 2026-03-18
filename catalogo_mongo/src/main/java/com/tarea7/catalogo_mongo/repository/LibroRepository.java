package com.tarea7.catalogo_mongo.repository;

import com.tarea7.catalogo_mongo.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LibroRepository extends MongoRepository<Libro, String> {

    List<Libro> findByAutor(String autor);

    List<Libro> findByTitulo(String titulo);

    List<Libro> findByGenero(String genero);
}

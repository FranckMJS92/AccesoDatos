package com.dam.demo.repository;

import com.dam.demo.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Un repositorio es la capa que habla con la BD
 * JpaRepository ya trae el CRUD básico:
 * - findAll
 * - findById
 * - save
 * - deleteById,
 * - exitsById
 */
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}

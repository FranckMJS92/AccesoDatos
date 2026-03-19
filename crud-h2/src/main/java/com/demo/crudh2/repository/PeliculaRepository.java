package com.demo.crudh2.repository;

import com.demo.crudh2.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Un repositorio es la capa que habla con la BD
 * JPARepository ya trae el CRUD basico
 * - findAll
 * - findById
 * - save
 * - deleteById
 * - existById
 *
 */
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}

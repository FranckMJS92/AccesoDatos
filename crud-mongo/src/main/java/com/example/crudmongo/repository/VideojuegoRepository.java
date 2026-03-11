package com.example.crudmongo.repository;

import com.example.crudmongo.model.Videojuego;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface  VideojuegoRepository extends MongoRepository<Videojuego, String> {

    //Ejemplo de consultas automaticas por metodo
    List<Videojuego> findByPlataforma(String plataforma);
    List<Videojuego> findByGenero(String genero);
}

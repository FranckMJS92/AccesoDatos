package com.example.crudmongo.controller;

import com.example.crudmongo.model.Videojuego;
import com.example.crudmongo.service.VideojuegoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService service;

    public VideojuegoController(VideojuegoService service) {
        this.service = service;
    }

    // GET http://localhost:8080/api/videojuegos
    @GetMapping
    public List<Videojuego> getAll() {
        return service.findAll();
    }

    // GET http://localhost:8080/api/videojuegos/{id}
    @GetMapping("/{id}")
    public Videojuego getById(@PathVariable String id) {
        return service.findById(id);
    }

    // POST http://localhost:8080/api/videojuegos
    @PostMapping
    public Videojuego create(@RequestBody Videojuego v) {
        return service.create(v);
    }

    // POST http://localhost:8080/api/videojuegos/{id}
    @PutMapping
    public Videojuego update(@PathVariable String id, @RequestBody Videojuego v) {
        return service.update(id, v);
    }

    // PUT http://localhost:8080/api/videojuegos/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    // GET http://localhost:8080/api/videojuegos/plataforma/PC
    @GetMapping("plataforma/{plataforma}")
    public List<Videojuego> byPlataforma(@PathVariable String plataforma) {
        return service.findByPlataforma(plataforma);
    }

    // GET http://localhost:8080/api/videojuegos/genero/RPG
    @GetMapping("genero/{genero}")
    public List<Videojuego> byGenero(@PathVariable String genero) {
        return service.findByGenero(genero);
    }
}

package com.tarea6.crudh2entregable.controller;

import com.tarea6.crudh2entregable.model.Videojuego;
import com.tarea6.crudh2entregable.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService service;

    public VideojuegoController(VideojuegoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Videojuego> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Videojuego getById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Videojuego create(@RequestBody Videojuego v) {
        return service.create(v);
    }

    @PutMapping("/{id}")
    public Videojuego update(@PathVariable long id, @RequestBody Videojuego v) {
        return service.update(id, v);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @GetMapping("plataforma/{plataforma}")
    public List<Videojuego> getByPlataforma(@PathVariable String plataforma) {
        return service.findByPlataforma(plataforma);
    }

    @GetMapping("puntuacion/{puntuacion}")
    public List<Videojuego> getByPuntuacion(@PathVariable double puntuacion) {
        return service.findByPuntuacion(puntuacion);
    }
}

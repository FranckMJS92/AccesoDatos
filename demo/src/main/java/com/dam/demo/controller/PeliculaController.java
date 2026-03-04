package com.dam.demo.controller;

import com.dam.demo.model.Pelicula;
import com.dam.demo.service.PeliculaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller recivbe peticiones y devuelve respuesta
 * 
 * @RestController -> devuelve JSON automaticamente
 */
@RestController
@RequestMapping("/api/peliculas") // ruta base
public class PeliculaController {

    private final PeliculaService service;

    public PeliculaController(PeliculaService service) {
        this.service = service;
    }

    // GET http://localhost:8080/api/peliculas
    @GetMapping
    public List<Pelicula> listar() {
        return service.findAll();
    }

    // GET http://localhost:8080/api/peliculas/1
    @GetMapping("/{id}")
    public Pelicula obtener(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST http://localhost:8080/api/peliculas
    // BODY JSON: {"tirulo":"", "director": "", "anyo": ""}
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // devuelve un 201 Created
    public Pelicula crear(@RequestBody Pelicula p) {
        return service.create(p);
    }

    // PUT http://localhost:8080/api/peliculas/1
    @PutMapping("/{id}")
    public Pelicula actualizar(@PathVariable Long id, @RequestBody Pelicula p) {
        return service.update(id, p);
    }

    // DELETE http://localhost:8080/api/peliculas/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 sin contenido
    public void borrar(@PathVariable Long id) {
        service.delete(id);
    }
}
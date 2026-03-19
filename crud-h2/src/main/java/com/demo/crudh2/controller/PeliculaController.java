package com.demo.crudh2.controller;

import com.demo.crudh2.model.Pelicula;
import com.demo.crudh2.service.PeliculaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller recibe peticiones HTTP y devuelve respuesta
 *
 * @RestController -> devuelve JSON automaticamente
 */

@RestController
@RequestMapping("/api/peliculas") //ruta base
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

    @GetMapping("/{id}")
    public Pelicula obtener(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Devuelve un 201 Created
    public Pelicula crear(@RequestBody Pelicula p) {
        return service.create(p);
    }

    @PutMapping("/{id}")
    public Pelicula actualizar(@PathVariable long id, @RequestBody Pelicula p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 sin contenido
    public void eliminar(@PathVariable long id) {
        service.delete(id);
    }
}

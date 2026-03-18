package com.tarea7.catalogo_mongo.controller;

import com.tarea7.catalogo_mongo.model.Libro;
import com.tarea7.catalogo_mongo.service.LibroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    @GetMapping
    public List<Libro> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Libro getById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public Libro create(@RequestBody Libro l) {
        return service.create(l);
    }

    @PutMapping
    public Libro update(@PathVariable String id, @RequestBody Libro l) {
        return service.update(id, l);
    }

    @DeleteMapping("/{id}")
    public void  delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("autor/{autor}")
    public List<Libro> byAutor(@PathVariable String autor) {
        return service.findByAutor(autor);
    }

    @GetMapping("titulo/{titulo}")
    public List<Libro> byTitulo(@PathVariable String titulo) {
        return service.findByTitulo(titulo);
    }

    @GetMapping("genero/{genero}")
    public List<Libro> byGenero(@PathVariable String genero) {
        return service.findByGenero(genero);
    }
}

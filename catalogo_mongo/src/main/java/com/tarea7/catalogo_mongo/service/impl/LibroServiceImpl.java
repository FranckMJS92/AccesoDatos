package com.tarea7.catalogo_mongo.service.impl;

import com.tarea7.catalogo_mongo.model.Libro;
import com.tarea7.catalogo_mongo.repository.LibroRepository;
import com.tarea7.catalogo_mongo.service.LibroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository repository;

    public LibroServiceImpl(LibroRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Libro> findAll() {
        return repository.findAll();
    }

    @Override
    public Libro findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Override
    public Libro create(Libro l) {
        l.setId(null);
        return repository.save(l);
    }

    @Override
    public Libro update(String id, Libro l) {
        Libro existing = findById(id);
        existing.setTitulo(l.getTitulo());
        existing.setAutor(l.getAutor());
        existing.setGenero(l.getGenero());
        existing.setPrecio(l.getPrecio());
        existing.setDisponible(l.isDisponible());
        existing.setPaginas(l.getPaginas());

        return repository.save(existing);
    }

    @Override
    public void delete(String id) {
        findById(id);

        repository.deleteById(id);
    }

    @Override
    public List<Libro> findByAutor(String autor) {
        return repository.findByAutor(autor);
    }

    @Override
    public List<Libro> findByTitulo(String titulo) {
        return repository.findByTitulo(titulo);
    }

    @Override
    public List<Libro> findByGenero(String genero) {
        return repository.findByGenero(genero);
    }
}

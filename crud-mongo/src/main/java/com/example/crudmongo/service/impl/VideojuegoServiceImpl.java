package com.example.crudmongo.service.impl;

import com.example.crudmongo.model.Videojuego;
import com.example.crudmongo.repository.VideojuegoRepository;
import com.example.crudmongo.service.VideojuegoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideojuegoServiceImpl implements VideojuegoService {

    private final VideojuegoRepository repo;

    public VideojuegoServiceImpl(VideojuegoRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Videojuego> findAll() {
        return repo.findAll();
    }

    @Override
    public Videojuego findById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado"));
    }

    @Override
    public Videojuego create(Videojuego v) {
        v.setId(null);
        return repo.save(v);
    }

    @Override
    public Videojuego update(String id, Videojuego v) {
        Videojuego existing = findById(id);
        existing.setTitulo(v.getTitulo());
        existing.setPlataforma(v.getPlataforma());
        existing.setGenero(v.getGenero());
        existing.setPuntuacion(v.getPuntuacion());

        return repo.save(existing);
    }

    @Override
    public void delete(String id) {
        findById(id);

        repo.deleteById(id);
    }

    @Override
    public List<Videojuego> findByPlataforma(String plataforma) {
        return repo.findByPlataforma(plataforma);
    }

    @Override
    public List<Videojuego> findByGenero(String genero) {
        return repo.findByGenero(genero);
    }
}

package com.tarea6.crudh2entregable.service.impl;

import com.tarea6.crudh2entregable.model.Videojuego;
import com.tarea6.crudh2entregable.repository.VideojuegoRepository;
import com.tarea6.crudh2entregable.service.VideojuegoService;
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
    public Videojuego findById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe videojuego"));
    }

    @Override
    public Videojuego create(Videojuego v) {
        v.setId(null);
        return repo.save(v);
    }

    @Override
    public Videojuego update(long id, Videojuego v) {
        Videojuego existe = findById(id);

        existe.setTitulo(v.getTitulo());
        existe.setPlataforma(v.getPlataforma());
        existe.setGenero(v.getGenero());
        existe.setAnyo(v.getAnyo());
        existe.setPrecio(v.getPrecio());
        existe.setPuntuacion(v.getPuntuacion());

        return repo.save(existe);
    }

    @Override
    public void delete(long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("No existe elemento a borrar con id " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public List<Videojuego> findByPlataforma(String plataforma) {
        return repo.findByPlataforma(plataforma);
    }

    @Override
    public List<Videojuego> findByPuntuacion(double puntuacion) {
        return repo.findByPuntuacion(puntuacion);
    }
}

package com.dam.demo.service.impl;

import com.dam.demo.model.Pelicula;
import com.dam.demo.repository.PeliculaRepository;
import com.dam.demo.service.PeliculaService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Esta clase implemento la logica de negocio
 */

@Service
public class PeliculaServiceImpl implements PeliculaService {

    private final PeliculaRepository repo;

    public PeliculaServiceImpl(PeliculaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Pelicula> findAll() {
        // Devuelve todas las filas de la tabla "peliculas"
        return repo.findAll();
    }

    @Override
    public Pelicula findById(Long id) {
        // findById devuelve Optional -> si no esta, lanzamos una excepción
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException(" No existe pelicula con ese id" + id));
    }

    @Override
    public Pelicula create(Pelicula p) {
        // Guardamos pelicula
        p.setId(null); // forzamos por seguridad qque sea una creación
        return repo.save(p);
    }

    @Override
    public Pelicula update(Long id, Pelicula p) {
        // PASO 1: Buscar si la pelicula existe, si no existe -> error
        Pelicula existe = findById(id);

        // PASO 2: Actualizar campos ( NO CAMBIAMOS EL ID)
        existe.setTitulo(p.getTitulo());
        existe.setDirector(p.getdirector());
        existe.setAnyo(p.getAnyo());

        // PASO 3: Guardar
        return repo.save(existe);
    }

    @Override
    public void delete(Long id) {
        // Comprobamos que existe, si no existe -> error
        if (!repo.existsById(id)) {
            throw new RuntimeException(" No se puede borrar, no existe pelicula con ese id" + id);
        }
        repo.deleteById(id);
    }
}
package com.demo.crudh2.service.impl;

import com.demo.crudh2.model.Pelicula;
import com.demo.crudh2.repository.PeliculaRepository;
import com.demo.crudh2.service.PeliculaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Esta clase implementa la logica del negocio
 */

@Service
public class PeliculaServiceImpl implements PeliculaService {

    private final PeliculaRepository repo;

    public PeliculaServiceImpl(PeliculaRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Pelicula> findAll() {
        // Devuelve todas las filas de la tabla pelicula
        return repo.findAll();
    }

    @Override
    public Pelicula findById(long id) {
        // findById devuelve Optionla -> si no esta, lanzamos una excepcion
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe pelicula con ese id"));
    }

    @Override
    public Pelicula create(Pelicula p) {
        // Guardamos pelicula
        p.setId(null); // forzamos por seguridad que sea una creacion
        return repo.save(p);
    }

    @Override
    public Pelicula update(long id, Pelicula p) {
        // PASO 1 - Buscar si la pelciula existe, si no existe -> error
        Pelicula existe = findById(id);

        // PASO 2 - ACtualizar campos {NO CAMBIAMOS EL ID}
        existe.setTitulo(p.getTitulo());
        existe.setDirector(p.getDirector());
        existe.setAnyo(p.getAnyo());

        // PASO3 - Guardar
        return repo.save(existe);
    }

    @Override
    public void delete(long id) {
        // Comporbamos que existe, si no existe -> error
        if (!repo.existsById(id)) {
            throw new RuntimeException("No se puede borrar, no existe pelicula con ese id " + id);
        }
        repo.deleteById(id);
    }
}

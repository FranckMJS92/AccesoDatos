package com.demo.crudh2.model;

import jakarta.persistence.*;

/**
 * Esta clase representa nuestro "modelo de datos" en java
 */

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id // marca este campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // la BD genera el id automaticamente
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String director;

    @Column(name = "anyo")
    private int anyo;

    //  Constructor vacio
    public Pelicula() {
    }

    public Pelicula(String titulo, String director, int anyo) {
        this.titulo = titulo;
        this.director = director;
        this.anyo = anyo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", director='" + director + '\'' +
                ", anyo=" + anyo +
                '}';
    }
}

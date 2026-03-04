package com.dam.demo.model;

import jakarta.persistence.*;

/*
*   Esta clase representa nuestro "modelo de datos" en java
 */
@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id // Marca este campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La BD genera el id automáticamente
    private Long id;

    @Column(nullable = false, length = 150) // Columna obligatoria (NOT NULL) y tamaño máximo 150
    private String titulo;

    @Column(nullable = false, length = 150) // Columna obligatoria (NOT NULL) y tamaño máximo 150
    private String director;

    @Column(name = "anyo") // Guardamos el año
    private int anyo;

    // Constructor vacío
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

    public String getdirector() {
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
        return "Película {" +
                "id = " + id +
                ", título = '" + titulo + '\'' +
                ", director = '" + director + '\'' +
                ", anyo = " + anyo +
                ']';
    }
}
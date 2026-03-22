package com.tarea6.crudh2entregable.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.Year;

@Entity
@Table(name = "videojuego")
public class Videojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 200)
    private String plataforma;

    @Column(nullable = false, length = 200)
    private String genero;

    @Column(name = "anyo")
    @Max(value = 2026, message = "El año no puede ser mayor a 2026")
    private int anyo;

    @Column
    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    @Column
    @Min(value = 1, message = "Puntuacion minima 1")
    @Max(value = 10, message = "Puntuacion maxima 10")
    private double puntuacion;

    public Videojuego() {
    }

    public Videojuego(String titulo, String plataforma, String genero, int anyo, double precio, double puntuacion) {
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.genero = genero;
        this.anyo = anyo;
        this.precio = precio;
        this.puntuacion = puntuacion;
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

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", plataforma='" + plataforma + '\'' +
                ", genero='" + genero + '\'' +
                ", anyo=" + anyo +
                ", precio=" + precio +
                ", puntuacion=" + puntuacion +
                '}';
    }
}

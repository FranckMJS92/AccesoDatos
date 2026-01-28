package Clase12;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.annotation.processing.Generated;
import java.util.HashSet;

public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //DATOS BASICO
    private String nombre;

    private LocalDate fechaContratation;

    @Embedded
    private Direccion direccion;

    // Un empleado pertenece a 0 o 1 departamento
    @ManyToOne
    private Departamento departamento;

    // Un empleado participa en muchos proyectos
    // Un proyecto tiene muchos participantes
    // mappedBy Indica que el dueño de la relacion sera "Proyecto.participantes"
    @ManyToMany(mappedBy = "participantes")
    private Set<Proyecto> proyectos = new HashSet<Proyecto>();

    // Un empleado puede ser jefe de 0 a 1 proyecto
    // mappedBy = "jefe" indica que el dueño es Proyecto.jefe
    @OneToMany(mappedBy = "jefe")
    private Set<Proyecto> proyectoDirigidos = new Set<Proyecto>();

    public Empleado() {
    }

    public Empleado(String nombre, LocalDate fechaContratacion, Direccion direccion) {
        this.nombre = nombre;
        this.fechaContratation = fechaContratacion;
        this.direccion = direccion;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaContratation() {
        return fechaContratation;
    }

    public void setFechaContratation(LocalDate fechaContratation) {
        this.fechaContratation = fechaContratation;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}

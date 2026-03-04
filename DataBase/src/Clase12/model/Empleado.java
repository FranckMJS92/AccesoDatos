package Clase12.model;

import jakarta.presistence.*;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.annotation.processing.Generated;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // DATOS BÁSICOS
    private String nombre;

    private LocalDate fechaContratation;

    @Embedded
    private Direccion direccion;

    // Un empleado pertene a 0 o 1 departamento.
    @ManyToOne
    private Departamento departamento

    // Un empleado participa en muchos proyectos
    // Un proyecto tiene muchos participantes
    // mappedBy indica que el dueño de la relacion sera "Proyecto.partipantes"
    @ManyToMany(mappedBy = "participantes")
    private Set<Proyecto> proyectos = new HashSet<>();

    // Un empleado puede ser jefe de 0 a 1 proyecto
    // mappedBy ="jefe" indica que el dueño es Proyecto.jefe
    @OneToMany(mappedBy = "jefe")
    private Set<Proyecto> proyectosDirigidos = new HashSet<>();

    public Empleado() {
    }

    public Empleado(String nombre, LocalDate fechaContatacion, Direccion direccion) {
        this.nombre = nombre;
        this.fechaContratation = fechaContatacion;
        this.direccion = direccion;
    }

    // Getters/setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratation;
    }

    public void setFechacontratacion(LocalDate fechaContratacion) {
        this.fechaContratation = fechaContratacion;
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

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public Set<Proyecto> getProyectoDirigidos() {
        return proyectosDirigidos;
    }

    @Transient
    public long getAntiguedadEnDias() {
        if (fechaContratation == null)
            return 0;
        return ChronoUnit.DAYS.between(fechaContratation, LocalDate.now());

    }
}
//package Clase3;

//Clase de datos
public class Modulo {
    // Atributos privados
    private String nombre;
    private int horas;
    private double nota;

    // Constructor para crear el objeto con sus valores
    public Modulo(String nombre, int horas, double nota) {
        this.nombre = nombre;
        this.horas = horas;
        this.nota = nota;
    }

    // Getters para poder leer los campos desde las otras clases
    public String getNombre() {
        return nombre;
    }

    public int getHoras() {
        return horas;
    }

    public double getNota() {
        return nota;
    }

}

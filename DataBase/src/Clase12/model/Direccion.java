package Clase12.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {

    private String calle;

    private int numero;

    public Direccion() {}

    public Direccion(String calle, int numero, String ciudad) {
        this.calle = calle;
        this.numero = numero;
    }

    public String getCalle() { 
        return calle; 
    }
    public int getNumero() { 
        return numero; 
    }
        
}

Modelo java

```java
public class Pelicula {
    private Integer id;
    private String titulo;
    private String director;
    private int anio;
    private double puntuacion;

    public Pelicula(
        Integer id, String titulo, String director, int anio, double puntuacion) {

        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.puntuacion = puntuacion;
    }
}
```

La tabla equivalente en SQL seria la siguiente: 
```sql
CREATE TABLE PELICULA (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100),
    director VARCHAR(100),
    anio INT,
    puntuacion DOUBLE
);
```
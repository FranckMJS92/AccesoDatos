# RESUMEN
```sql
CREATE TABLE Peli(
    idPeli  INT(11) NOT NULL AUTO_INCREMENT,
    titulo  VARCHAR(45) NOT NULL,
    anyo    INT NOT NULL,
    director VARCHAR(45) NOT NULL,
    PRIMARY KEY (idPeli)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

```java
public class Peli implements Serializable {
    private Long idPeli;
    private String titulo;
    private int anyo;
    private String elDirector;
    // constructores, getters, setters...
}
```

El problema que presenta Hibernate/JPA es la necesidad de conocer como se corresponden:
- La Clase Peli con la tabla Peli
- Cada atributo (idPeli, titulo, anyo, elDirector) con sus columnas.

Esta traduccion entre el objeto y la tabla es el MAPEO (mapping).
## 1. Primera Forma de Mapear: archivo .hmb.xml
Archivo de mapeo .hbm.xml -> 3.1
Para la clae Peli se crea un ficheo XML externo, por ejemplo Peli.hbm.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
  "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
  "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping> 

    <class name="Model.Peli" table="Peli">
        <id column="idPeli" name="idPeli" type="long">
            <generator class="native" />
        </id>

        <property name="titulo"   type="string"/>
        <property name="anyo"     type="int"/>
        <property name="elDirector" column="director" />
    </class>

</hibernate-mapping>
```
Que significa cada cosa:
- <hibernate-mapping> Raiz del fichero de mapeo
- <class name="Model.Peli" table="Peli">  name -> nombre completo d ela clase Java y table -> nombre de la tabla en la Base de Datos donde se guardan los objetos de esa clase.
- <id...> Mapea la clave primaria de la tabla
    - column="idPeli" -> nombre de la columna de la tabla
    - name="idPeli" -> atributo de la clase que corresponde con esa columna.
    - type= "long" -> tipo Java del atributo
- <generator class="native"> Le dice a Hibernate cómo se genera el id:
    - native =  dejab que el SGBD use su mecanismo (AUTO_INCREMENT).
- <property ...> Mapea un atributo normal de la clase.
    - name="titulo" -> atrubyto de la clase 
    - column= "director" -> opcional, solo su el nombre de la columna no coincide con el del atributo.
    - type="string", type="int" -> tipos Java.

## 2. Segunda forma de Maper: mapeo con anotacions JPA
```java
import jakarta.peristence.*;
import java.io.Serializable;

@Entity                         // esta clase es una entidad JPA
@Table(name = "Peli")           // Se guarda en la tabla Peli
public class Pelicula implements Serializable {

    @Id                          // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeli          // AUTO_INCREMENT

    @Column
    private String titulo;       //nombre de la columna = titulo

    @Column
    private int anyo;            //nombre de la columna = anyo

    @Column(name = "director")
    private String elDirector;   //Columna distinta al atributo
}
```
- @Entity indicar que la clae es una entidad persistente (Hibernate/JPA lo gestionará como tabla)
- @Table(name = "Peli") Indica el nombre de la tabla, si se omite, por defecto se usará el nombre de la clase "Pelicula"
- @Id Marca el atributo que será la clave primaria
- @GeneratedValue(strategy = GenerationType.IDENTITY) Indica que el id se genera automáticamente:
    IDENTITY suele corresponder con el AUTO_INCREMENT en la BB.
- @Column Mapea un atributo a una columna, si no se pone nada, usa el mismo nombre que el atributo, y su se pone @Column(name = "director") indicamos una columna con nombre diferente.

## 3. Componente embebidos: @Embeddable y @Embedded
```java
import jakarta.peristence.*;
import java.io.Serializable;
@Embeddable                                              // Esta clase NO tiene tabla propia
public class IMBD implements Serializable {

    
    @Column
    private String url;      

    @Column
    private double nota;            

    @Column
    private long votos; 
}
```
```java
import jakarta.peristence.*;
import java.io.Serializable;

@Entity                         // esta clase es una entidad JPA
@Table(name = "Peli")           // Se guarda en la tabla Peli
public class Pelicula implements Serializable {

    @Id                          // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeli          // AUTO_INCREMENT

    @Column
    private String titulo;       //nombre de la columna = titulo

    @Column
    private int anyo;            //nombre de la columna = anyo

    @Column(name = "director")
    private String elDirector;   //Columna distinta al atributo

    @Embedded                    // Metemos las columnas de IMBF en la tabla Peli
    private IMBD imbd;

    public Peli_IMBD(String titulo, int anyo, String elDiterctor, IMBN imbd){
        this.titulo = titulo;
        this.anyo = anyo;
        this.elDirector = elDirector;
        this.imbd = imbd;
    }
}
```
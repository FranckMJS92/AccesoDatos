## Sección 3 BSOO NATIVAS. OBJECTDB
Conceptos:
- BDR (relacionales)
- BDOR (objeto-relaciones)
- BDOO (orientadas a objetos)
### 3.1 Instalación y acceso a ObjectDB
¿Por qué ObjectDB?

- Es una BDOO nativa, no una adaptación relacional.
- Es ligera, gratuita para docencia y no necesita servidor.
- Se integra completamente con JPA, lo que la hace ideal para:
    - reforzar conceptos vistos en Hibernate,
    - mostrar que JPA no es Hibernate, sino una especificación.

Instalación

A diferencia de PostgreSQL o MySQL:

- No hay instalación de servidor
- No hay usuario/contraseña
- No hay esquema ni tablas

Solo necesitamos:

- La JVM
- El fichero objectdb.jar
- Un archivo de configuración JPA (persistence.xml)

ObjectDB se distribuye como:

- Un JAR ejecutable
- Con herramientas de administración
- Con librerías cliente

Acceso a la base de datos

La conexión a ObjectDB se realiza mediante JPA, no con JDBC.

El flujo es:
```java
EntityManagerFactory emf =
    Persistence.createEntityManagerFactory("nombreUnidad");

EntityManager em = emf.createEntityManager();
```
Comparación con JDBC:

| JDBC          | ObjectDB / JPA |
| ------------- | -------------- |
| DriverManager | Persistence    |
| Connection    | EntityManager  |
| SQL           | JPQL           |
| Tabla         | Clase          |
| Fila          | Objeto         |

Centralización del acceso

Tal y como hicimos con:
- ConexionBD en JDBC
- HibernateUtil en Hibernate

Aquí también se recomienda centralizar:
- Creación del EntityManagerFactory
- Creación de EntityManager
- Gestión de transacciones

Esto no es casual, todas las tecnologías de persistencia comparten este patrón.

### 3.2 Creación y persistencia de objetos

Este apartado es el núcleo conceptual de las BDOO.
#### Persistir clases

Para que una clase sea persistente en ObjectDB:

1. Se anota con @Entity
2. Tiene un identificador (@Id)
3. Opcionalmente, el ID se genera automáticamente

Ejemplo conceptual:
```java
@Entity
public class Alumno {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
}
```

¿Cuándo se guarda realmente el objeto?
```java
Alumno alu = new Alumno("Antonio");
em.persist(alu);
```
Esto NO guarda aún el objeto, el guardado real ocurre en: 
```java
em.getTransaction().commit();
```

Igual que en JDBC con transacciones y que Hibernate con commit()

#### Transacciones

En ObjectDB:
- Toda modificación debe ir en una transacción
- Se garantiza:
    - atomicidad
    - consistencia
    - rollback automático si algo falla

```java
em.getTransaction().begin();
em.persist(alu);
em.getTransaction().commit();
```
### 3.3 Clases embebidas o componentes

Este apartado enlaza directamente con:

- @Embeddable en Hibernate
- Estructuras en PostgreSQL
- Documentos anidados en Mongo (pero no son lo mismo)

¿Qué es una clase embebida?

Una clase embebida:
- No es una entidad
- No tiene ID
- No existe sola en la BD
- Vive dentro de otra entidad

Ejemplo conceptual:
```java
@Embeddable
public class Direccion {
    private String calle;
}
```
Y en la entidad: 
```java
@Entity
public class Alumno {
    @Embedded
    private Direccion direccion;
}
```
Resultado en la BD:
- Se guarda un Alumno
- La dirección forma parte del Alumno
- No hay entidad Direccion independiente

Comparación importante:
| Tecnología    | ¿Embebido?                  |
| ------------- | --------------------------- |
| JDBC          | ❌                           |
| Hibernate/JPA | ✔ (`@Embedded`)             |
| MongoDB       | ✔ (documentos anidados)     |
| ObjectDB      | ✔ (objeto dentro de objeto) |

### 3.4 Relaciones

ObjectDB soporta todas las relaciones clásicas del modelo OO:

#### Relación 1 a 1

Un objeto contiene exactamente otro.

Ejemplo:

- Clase → Tutor
- Alumno → Dirección (aunque aquí es embebido)
```java 
@OneToOne(cascade = CascadeType.PERSIST)
private Profesor tutor;
```
Cascade:
- Si guardo la Clase
- Se guarda automáticamente el Profesor

#### Relación 1 a muchos

Un objeto tiene una colección de otros.
```java
@OneToMany(cascade = CascadeType.PERSIST)
private List<Alumno> alumnos;
```
Fetch:
- EAGER: se cargan siempre
- LAZY: se cargan solo cuando se accede

Exactamente igual que Hibernate.

#### C. Relación muchos a muchos

Dos entidades se relacionan mediante colecciones mutuas.
```java
@ManyToMany
private Set<Profesor> profesores;
```

### 3.5 Consultas
Búsqueda por ID
```java
Alumno a = em.find(Alumno.class, 1L);
```
Muy importante:

- find devuelve el objeto
- No devuelve filas ni columnas

JPQL (Java Persistence Query Language)

Es un lenguaje:
- Orientado a clases
- Orientado a atributos
- Independiente del motor

```java 
TypedQuery<Alumno> q =
    em.createQuery("select a from Alumno a", Alumno.class);
```

Paralelismo:

- SQL → tablas
- JPQL → clases

Parámetros
´´´java
q.setParameter("nombre", "Antonio");
´´´
- Evita hard-code
- Evita inyección
- Mejora legibilidad

### 3,6 Borrado y actualización
Actualizar
```java
Alumno a = em.find(Alumno.class, 1L);
a.setNombre("Nuevo nombre");
em.getTransaction().commit();
```
- No hay UPDATE
- El objeto está gestionado

Borrar
```java
em.remove(a);
em.getTransaction().commit();
```
Si hay relaciones:
- cuidado con CascadeType.REMOVE

### RESUMEN
- ObjectDB es una BDOO nativa
- El modelo es 100% orientado a objetos 
- Se elimina el desfase objeto-relacional
- JPA se usa como API estándar
- Persistir = trabajar con objetos
- Consultar = recuperar objetos
- No se piensa en tablas, columnas ni joins
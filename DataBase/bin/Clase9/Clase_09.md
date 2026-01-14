## Sección 4. Mapeo de relaciones en Hibernate
Hasta ahora hemos visto cómo:
- Una tabla se mapea a una clase.
- Una fila se mapea a un objeto.
- Una columna se mapea a un atributo.

Pero en bases de datos reales las tablas no viven aisladas:
se relacionan entre sí mediante claves primarias (PK) y claves externas (FK).

### 4.1 Direccionalidad de las relaciones

Antes de hablar de tipos de relaciones (1:1, 1:N, N:M), es fundamental entender la direccionalidad.

Relación unidireccional
- Solo se puede navegar la relación en un sentido.
- Un objeto “conoce” al otro, pero no al revés.

Ejemplo conceptual:
- Un Grupo tiene un Tutor
- Desde Grupo puedo acceder a Profesor
- Desde Profesor no puedo acceder al Grupo

Es más simple y suele ser suficiente en muchos casos.

Relación bidireccional
- Se puede navegar la relación en ambos sentidos.
- Ambos objetos se conocen entre sí.

Ejemplo conceptual:
- Un Profesor puede saber qué Grupo tutoriza
- Un Grupo puede saber quién es su Profesor

Es más potente, pero más compleja, porque Hibernate debe saber:

- Quién es el propietario real de la relación
- Quién solo refleja esa relación

### 4.2 Relaciones uno a uno (@OneToOne)

Una relación 1:1 indica que:

- Una fila de una tabla se relaciona con una única fila de otra tabla.
- Y viceversa.

Ejemplo conceptual
- Grupo ↔ Profesor
- Un grupo tiene un tutor
- Un profesor tutoriza como máximo un grupo

#### Relación 1:1 unidireccional

Características clave:
- Solo una entidad define la relación
- Esa entidad contiene la clave externa

En Hibernate:
- Se usa @OneToOne
- Se usa @JoinColumn para indicar la FK
- La entidad que tiene la FK es la propietaria

Idea fundamental:

La propiedad de la relación siempre está en el lado que tiene la clave externa

#### Relación 1:1 bidireccional

Aquí:
- Ambas clases tienen referencia la una a la otra
- Solo una es propietaria
- La otra usa mappedBy

Conceptos importantes:
- mappedBy indica:

“Yo no gestiono la relación, solo la reflejo”

- Evita que Hibernate cree dos claves externas

Regla simple:
- Si ves mappedBy, esa clase NO es propietaria

#### Cascada (CascadeType)

La cascada define qué operaciones se propagan entre entidades relacionadas.

Ejemplo:
- Si guardo un Grupo
- ¿Se guarda también su Profesor?

Tipos más importantes
| Tipo      | Significado                                         |
| --------- | --------------------------------------------------- |
| `PERSIST` | Al guardar una entidad, se guardan las relacionadas |
| `MERGE`   | Se propagan actualizaciones                         |
| `REMOVE`  | Al borrar, se borran las relacionadas               |
| `ALL`     | Aplica todos los anteriores                         |

Advertencia importante:

CascadeType.ALL es cómodo, pero peligroso si no se entiende bien.

### 4.3 Relaciones uno a muchos / muchos a uno

(@OneToMany / @ManyToOne)

Este es el caso más habitual en bases de datos.

Ejemplo conceptual
- Un Autor escribe muchos Libros
- Un Libro pertenece a un solo Autor

Relación real en BD:
- La FK está en Libro

#### Relación unidireccional
- Solo Libro conoce a Autor
- Autor no tiene lista de libros

En Hibernate:
- @ManyToOne en Libro
- @JoinColumn define la FK

Muy común en aplicaciones simples.

#### Relación bidireccional
- Libro tiene un Autor
- Autor tiene un Set<Libro>

Aquí:
- Libro es el lado propietario
- Autor usa mappedBy

#### Fetch (FetchType)

Define cuándo se cargan los datos relacionados.
| Tipo    | Comportamiento                  |
| ------- | ------------------------------- |
| `EAGER` | Se cargan automáticamente       |
| `LAZY`  | Se cargan solo cuando se accede |

### 4.4 Relaciones muchos a muchos (@ManyToMany)

Una relación N:M implica una tabla intermedia.

Ejemplo conceptual:
- Un Profesor imparte varios Módulos
- Un Módulo puede ser impartido por varios Profesores

En BD:
- Tabla intermedia: Docencia

### RESUMEN
Conceptos fundamentales

- Hibernate permite mapear relaciones entre tablas como relaciones entre objetos.
- Toda relación tiene:
    - Cardinalidad (1:1, 1:N, N:M)
    - Direccionalidad (uni / bi)
    - Propietario

- @JoinColumn indica la clave externa.
- mappedBy indica que no soy propietario.
- CascadeType controla propagación de operaciones.
- FetchType controla cuándo se cargan los datos.
- Las relaciones N:M usan tablas intermedias.


## Sección 5. Consultas con HQL (Hibertnate Query Languege)

### ¿Qué es HQL y para qué sirve?
¿Por qué no usar SQL directamente?

Hibernate es una herramienta ORM. Eso significa que:
- La aplicación trabaja con objetos Java.
- La base de datos trabaja con tablas.

Si usáramos SQL directamente:
- Volveríamos a pensar en tablas, columnas y claves.
- Romperíamos parte de la abstracción que nos ofrece Hibernate.

HQL nace para resolver esto.
#### ¿Qué es HQL?

HQL (Hibernate Query Language) es un lenguaje de consultas:
- Similar a SQL en sintaxis
- Pero que trabaja sobre entidades y atributos, no sobre tablas y columnas

Ejemplo conceptual:
| SQL                    | HQL                      |
| ---------------------- | ------------------------ |
| `SELECT * FROM alumno` | `SELECT a FROM Alumno a` |
| Tablas                 | Clases                   |
| Columnas               | Atributos                |
| Filas                  | Objetos                  |

Idea clave:
Con HQL recuperamos objetos Java, no filas de una tabla.

### 5.1 Recuperación de objetos simples

Este tipo de consultas permiten:
- Obtener un objeto
- Obtener una lista de objetos
- Recuperar entidades completas mapeadas con Hibernate

Características importantes:
- Se ejecutan a través de la interfaz Query
- El resultado suele ser:
    - Una lista (List<T>)
    - O un único objeto (uniqueResult())

```java
Consulta HQL → Hibernate → Objetos Java
```

Aspectos clave
- Si la consulta devuelve varios resultados, usamos listas.
- Si devuelve uno solo, usamos uniqueResult().
- Si uniqueResult() devuelve más de uno → excepción.

### 5.2 Consultas mixtas (parciales)

No siempre necesitamos el objeto completo.

Las consultas mixtas:
- Devuelven solo algunos atributos
- No devuelven entidades completas
- El resultado es un array de Object (Object[])

Ejemplo conceptual:

```sql
SELECT nombre, edad FROM Alumno
```
Resultado:
```java
Object[] { nombre, edad }
```

Importante:
- Ya no estamos trabajando con entidades
- Estamos trabajando con datos sueltos
- Perdemos tipado fuerte

Se usan para:
- Informes
- Listados
- Consultas optimizadas

### 5.3 Select encadenados (relaciones y FETCH)

Cuando una entidad tiene relaciones (@OneToMany, @ManyToOne, etc.):
- Hibernate no carga todo automáticamente
- Usa estrategias de carga:
    - LAZY (por defecto)
    - EAGER

Problema habitual

Si no entendemos esto:
- Parece que “faltan datos”
- O se lanzan excepciones al acceder fuera de sesión

Concepto importante:

HQL permite controlar cómo se cargan las relaciones
(especialmente con fetch)

### 5.4 Consultas sobre colecciones

Cuando una entidad contiene:
- Listas
- Sets
- Colecciones mapeadas

HQL permite:
- Consultar el tamaño (size())
- Comprobar si están vacías
- Filtrar por elementos internos

Esto es clave en modelos ricos, muy habituales en Hibernate.

### 5.5 Consultas con parámetros
Problema del hard-coded

Si escribimos:
```sql
where id = 3
```

Tenemos problemas:
- Código poco reutilizable
- Riesgo de inyección
- Difícil mantenimiento

Solución: parámetros
HQL permite:
- Parámetros posicionales (?)
- Parámetros nominales (:nombre)

Muy importante didácticamente:

Esto conecta directamente con lo visto en JDBC y PreparedStatement.

### 5.6 Inserciones, actualizaciones y borrados con HQL

Hibernate no está pensado para CRUD masivo con HQL, pero lo permite:
- insert into
- update
- delete

Características:
- No devuelven objetos
- Devuelven número de filas afectadas
- Se ejecutan con executeUpdate()

Estas operaciones existen, pero Hibernate prefiere trabajar con objetos (save, update, delete).

### RESUMEN
| Concepto           | Idea clave                                |
| ------------------ | ----------------------------------------- |
| HQL                | Lenguaje de consultas orientado a objetos |
| Diferencia con SQL | Trabaja con clases y atributos            |
| Resultado          | Objetos Java o `Object[]`                 |
| Query              | Interfaz para ejecutar HQL                |
| Parámetros         | Evitan hard-coded e inyección             |
| Relaciones         | Se pueden controlar con HQL               |
| CRUD con HQL       | Posible, pero no principal                |

# Tema 3.
## Sección 1. Herramientas de mapeo. Características

### 1.1 ¿Qué hace exactamente un ORM?

Un ORM (Object-Relational Mapping) es una herramienta o framework encargado de:

1. Definir cómo se relacionan objetos con tablas

Incluye un conjunto de descripciones y metadatos que establecen correspondencias:

| En Java                  | En la Base de Datos |
| ------------------------ | ------------------- |
| Clases                   | Tablas              |
| Atributos                | Columnas            |
| Objetos                  | Filas (tuplas)      |
| Relaciones entre objetos | Claves foráneas     |


Pero esta correspondencia a veces no es directa. Pueden existir:

- Propiedades en Java que no deben guardarse en la BD.

- Columnas en la BD que no aparecen en los objetos.

- Tipos incompatibles (por ejemplo, listas, objetos complejos, booleanos, enumerados…).

El ORM se encarga de traducir estos elementos automáticamente.

2.  Mantener la persistencia de los objetos

Los objetos se guardan:

- en memoria (mientras se usan)

- y sincronizados con la base de datos (cuando se persisten)

Cuando actualizamos un objeto, el ORM sabe qué cambios debe reflejar en la BD sin que escribamos SQL manual.

### 1.2 ¿Cómo funciona una arquitectura ORM?

   Clase 1        Clase 2        Clase n
     ↓              ↓              ↓
  Mapeo 1        Mapeo 2        Mapeo n
     ↘             ↙              ↘
  Marco de persistencia objeto-relacional (ORM)
                      ↓
               Conector JDBC
                      ↓
                 Base de datos

Explicación:

- Cada clase de tu programa tiene una configuración que indica cómo se guarda en la BD.

- El ORM contiene un motor de persistencia que:

    - detecta cambios

    - consulta la BD

    - inserta, actualiza, borra

    - gestiona transacciones

- Incluso usando un ORM, siempre se usa JDBC internamente para comunicarse con el SGBD.

Ventajas principales de usar un ORM
| Ventaja                          | Explicación                                                            |
| -------------------------------- | ---------------------------------------------------------------------- |
| **Ahorra tiempo de desarrollo**  | Evita escribir SQL repetitivo y código para convertir objetos ↔ filas. |
| **Permite abstracción del SGBD** | No importa si es MySQL, PostgreSQL o H2; el ORM usa JDBC internamente. |
| **Manipulas objetos, no tablas** | Trabajas de forma natural en Java sin pensar continuamente en SQL.     |

Costes y desventajas
No todo es perfecto:

- El ORM traduce consultas a SQL, lo que puede penalizar el rendimiento.

- La “magia” del ORM a veces oculta detalles importantes, por lo que debes entender cómo trabaja para evitar sorpresas.

- Es fácil cometer errores si no se gestionan correctamente:
    - cargas perezosas (lazy loading)
    - transacciones
    - relaciones complejas (1:1, 1:N, N:M)

### 1.3 Componentes de una herramienta ORM

La teoría distingue tres pilares esenciales:
| Componente                                   | Qué hace                                                                       |
| -------------------------------------------- | ------------------------------------------------------------------------------ |
| **Técnicas de mapeo**                        | Definen cómo se transforman clases ↔ tablas.                                   |
| **Lenguaje de consulta orientado a objetos** | SQL adaptado al paradigma orientado a objetos (por ejemplo, HQL).              |
| **Técnicas de sincronización**               | Mantienen los objetos en memoria sincronizados con su representación en la BD. |
A. Técnicas de mapeo

Dos enfoques habituales:
- Incrustar la definición de mapeo dentro del código, usando:
    - anotaciones Java (@Entity, @Column, etc.)
    - macros o atributos especiales

- Ficheros externos XML, donde se escriben las relaciones clase–tabla.

Ambos métodos conviven en la mayoría de ORMs, incluido Hibernate.

B. Lenguaje de consulta (OQL)

Los ORMs suelen incorporar su propio lenguaje de consulta, llamado:
    
    OQL - Objecto query Language
Es similar a SQL pero pensado para trabajar con objetos en lugar de con tablas.

En Hibernate, este lenguaje se llama:
    
    HQL – Hibernate Query Language

Ejemplo conceptual:
```sql
FROM Persona p WHERE p.edad > 20
```

C. Técnicas de sincronización

Es una de las partes más complejas del ORM. Incluye:
- Detección de cambios en objetos

El ORM analiza qué propiedades del objeto han cambiado desde que fue cargado.

- Crear nuevas instancias desde la BD

Cuando consultamos datos, el ORM crea los objetos que representan las filas.

- Reflejar cambios en la BD

A partir de las modificaciones de los objetos, se generan sentencias SQL:
   * INSERT
    - UPDATE
    - DELETE

Es decir, el ORM decide cuándo sincronizar los objetos con la base de datos.

### 1.4 Hibernate

Hibernate es uno de los frameworks ORM más conocidos en el ecosistema Java.

Objetivo de Hibernate

Facilitar:
- la definición del modelo de datos (clases, atributos y relaciones)
- la persistencia automática en una base de datos relacional
- una forma de consultar datos mediante HQL
- el uso de JDBC sin escribir código JDBC

 Arquitectura interna de Hibernate

Application
 ├─ SessionFactory
 │   ├─ TransactionFactory
 │   └─ ConnectionProvider (usa JDBC)
 └─ Session
      └─ Transaction
Database

| Componente         | Función                                                                |
| ------------------ | ---------------------------------------------------------------------- |
| **SessionFactory** | Fábrica de sesiones. Se crea una vez por aplicación.                   |
| **Session**        | Representa una conexión lógica con la BD. Maneja objetos persistentes. |
| **Transaction**    | Controla la ejecución atómica de operaciones.                          |
| **Hibernate/JDBC** | Hibernate sigue usando JDBC por debajo.                                |

Tipos de objetos

Hibernate distingue:

- Transient objects → existen solo en la memoria, no están guardados en la BD.
- Persistent objects → están sincronizados con una base de datos.
- Detached objects → estuvieron sincronizados, pero ya no lo están.

### Resumen final de la sección
| Concepto                      | Idea principal                                                                 |
| ----------------------------- | ------------------------------------------------------------------------------ |
| **Desfase objeto-relacional** | Diferencia entre trabajar con objetos y trabajar con tablas SQL.               |
| **ORM**                       | Herramienta que resuelve ese desfase automatizando el mapeo y la persistencia. |
| **Mapeo**                     | Cómo se relacionan clases ↔ tablas, atributos ↔ columnas.                      |
| **OQL/HQL**                   | Lenguaje de consulta orientado a objetos.                                      |
| **Sincronización**            | Proceso de reflejar en la BD los cambios realizados en objetos.                |
| **Hibernate**                 | ORM para Java que proporciona sesiones, transacciones y mapeo automático.      |

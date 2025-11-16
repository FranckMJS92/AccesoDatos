# Tema 2.
## Sección 1. El desfase objeto-relacional
### 1. El modelo relacional

- Es el modelo utilizado por la mayoría de Sistemas Gestores de Bases de Datos Relacionales (SGBDR) como MySQL, Oracle, PostgreSQL o SQL Server.

- Organiza la información en tablas (entidades), filas (registros) y columnas (atributos).

- Las tablas se relacionan entre sí mediante:

    - Claves primarias (PK) → identifican de forma única cada registro.

    - Claves foráneas (FK) → establecen relaciones entre tablas.

- El modelo relacional se centra en los datos y sus relaciones.

- Es eficiente, estandarizado y persistente, pero su estructura rígida puede complicar la representación de datos más complejos.

```
Tabla ALUMNOS
+------------+---------+------+
| id_alumno  | nombre  | edad |
+------------+---------+------+
| 1          | Sergio  | 20   |
| 2          | Luis    | 29   |
+------------+---------+------+

Tabla CURSOS
+-----------+-----------+
| id_curso  | nombre    |
+-----------+-----------+
| 101       | DAM       |
| 102       | DAW       |
+-----------+-----------+

Relación: ALUMNOS.id_curso → CURSOS.id_curso
```
#### 1.2. El modelo orientado a objetos

- Representa la información a través de clases y objetos, que combinan propiedades (atributos) y comportamientos (métodos).

- Los objetos son instancias de clases y se relacionan entre sí mediante asociaciones, herencia o composición.

- Este modelo se centra en los objetos y sus operaciones, no solo en los datos.

```java
class Alumno {
    private int id;
    private String nombre;
    private int edad;

    // Constructor y métodos
    public Alumno(int id, String nombre, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
    }
}
```

#### 1.3. Diferencias clave entre ambos modelos

| Concepto      | Modelo Relacional                           | Modelo Orientado a Objetos         
| ------------- | ------------------------------------------- | --------------------------------------------- |
| Unidad básica | Tabla                                       | Clase                                         |
| Registro      | Fila                                        | Objeto                                        |
| Campo         | Columna                                     | Atributo                                      |
| Relaciones    | Claves externas                             | Referencias o asociaciones                    |
| Enfoque       | Datos estáticos                             | Objetos dinámicos con comportamiento          |
| Herencia      | No nativa (simulada con tablas)             | Propia del lenguaje                           |
| Encapsulación | No existe (todo es accesible por consultas) | Total (atributos privados y métodos públicos) |


### 2. El problema del desfase objeto-relacional

Cuando intentamos guardar objetos Java en una base de datos relacional, nos encontramos con un problema:
los objetos no se corresponden directamente con filas o tablas.

Por ejemplo:

- Un objeto Alumno tiene atributos (nombre, edad) y métodos (estudiar(), aprobar()).

- En la base de datos, solo podemos guardar valores, no comportamientos.

Además:

- Java usa referencias para relacionar objetos, mientras que SQL usa claves foráneas.

- Java tiene herencia y polimorfismo, que SQL no soporta directamente.

- Los datos se guardan en tablas planas, mientras que en Java los objetos pueden estar anidados o compuestos.

Esto se conoce como el Object–Relational Impedance Mismatch (Desfase Objeto–Relacional).

### 3. Consecuencias del desfase

Necesitamos convertir objetos en registros (y viceversa).

Debemos gestionar manualmente las relaciones entre tablas y objetos (JOINs ↔ asociaciones).

Se pierde parte del encapsulamiento y herencia de la POO al traducir los datos.

Se complica el mantenimiento y la evolución del código.

### RESUMEN
| Concepto                      | Definición / Observación                                                                   |
| ----------------------------- | ------------------------------------------------------------------------------------------ |
| **SGBDR**                     | Sistema Gestor de Bases de Datos Relacional. Usa SQL para gestionar datos en tablas.       |
| **Modelo Relacional**         | Representa los datos en tablas con filas y columnas. Basado en relaciones entre entidades. |
| **Modelo OO**                 | Representa los datos mediante clases y objetos. Añade comportamiento (métodos).            |
| **Clave Primaria (PK)**       | Identifica de forma única un registro.                                                     |
| **Clave Foránea (FK)**        | Relaciona una tabla con otra.                                                              |
| **Encapsulación**             | Propiedad OO que oculta los datos y solo los expone mediante métodos públicos.             |
| **Herencia**                  | Permite definir clases que heredan atributos y métodos de otras.                           |
| **Desfase objeto-relacional** | Dificultad de traducir entre objetos Java y tablas SQL.                                    |
| **Persistencia**              | Capacidad de un objeto de mantener su estado más allá de la ejecución del programa.        |


## Sección 2. Gestión a SGBD
### 1. Protocolos de acceso a bases de datos JDBC.
Cuando queremos que una aplicación Java acceda a una base de datos, necesitamos un protocolo común de comunicación.
A lo largo de la historia han existido dos grandes normas:
| Protocolo                             | Descripción                                                                                                                       | Soporte en Java                                                                                                     |
| ------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------- |
| **ODBC (Open Database Connectivity)** | API desarrollada por Microsoft para permitir a programas acceder a bases de datos mediante SQL, independientemente del proveedor. | Java puede usar ODBC mediante puentes o adaptadores, pero no lo incluye directamente.                               |
| **JDBC (Java Database Connectivity)** | API oficial de Java para conectar programas con bases de datos relacionales. Ofrece independencia del sistema gestor.             | Es la opción estándar en Java; permite conectar cualquier aplicación Java con cualquier SGBD que tenga driver JDBC. |


NOTA: JDBC es una interfaz universal que traduce las órdenes SQL de nuestra aplicación Java al lenguaje que entiende el gestor de base de datos.

### 2. Arquitectura de JDBC.
JDBC actúa como una capa intermedia entre la aplicación Java y el SGBD.
No accedemos directamente a la base de datos: lo hacemos a través del driver JDBC.

Flujo general

1. La aplicación Java hace una petición (por ejemplo, un SELECT).

2. Esa petición se envía a la API JDBC.

3. La API JDBC se comunica con un Driver Manager, que se encarga de buscar el controlador adecuado.

4. El Driver JDBC específico (MySQL, Oracle, PostgreSQL…) traduce la orden a la base de datos correspondiente.

```java
Aplicación Java
       ↓
     API JDBC
       ↓
 Gestor de Drivers
       ↓
 Driver JDBC (MySQL / Oracle / SQLite / etc.)
       ↓
   Base de Datos
```

Esto permite cambiar de base de datos sin modificar el código Java, solo cambiando el driver y la cadena de conexión.

#### 2.1 Tipos de driver JDBC
Java clasifica los controladores JDBC en cuatro tipos principales:
| Tipo                            | Nombre                                                                    | Características                                              |
| ------------------------------- | ------------------------------------------------------------------------- | ------------------------------------------------------------ |
| **Tipo I – Puente JDBC-ODBC**   | Usa ODBC como intermediario. Necesita software externo.                   | Antiguo y en desuso.                                         |
| **Tipo II – API Nativa**        | Utiliza bibliotecas del sistema operativo.                                | Rápido, pero dependiente del sistema.                        |
| **Tipo III – Protocolo de red** | Traduce llamadas JDBC a un protocolo intermedio para un servidor.         | Flexible, pero más complejo.                                 |
| **Tipo IV – Java puro (100%)**  | Escrito completamente en Java. Conecta directamente con la base de datos. | El más usado actualmente. No necesita instalación adicional. |

### 3. Bases de datos embebidas.
Una base de datos embebida es un SGBD que se integra dentro de la propia aplicación.
No requiere instalación ni un servidor aparte; los datos se guardan directamente en un archivo dentro del proyecto.

Estas bases de datos son ideales para:

- Aplicaciones de escritorio.

- Programas educativos o de demostración.

- Tests o pequeños prototipos.

| Base de datos | Descripción                                                            | Características                                         |
| ------------- | ---------------------------------------------------------------------- | ------------------------------------------------------- |
| **SQLite**    | Motor SQL liviano basado en un solo archivo.                           | Sin servidor, multiplataforma, ideal para apps locales. |
| **H2**        | Implementado 100% en Java, puede trabajar en modo embebido o servidor. | Perfecto para proyectos educativos o pruebas unitarias. |
| **ObjectDB**  | Base de datos orientada a objetos, no relacional.                      | Guarda directamente objetos Java (sin SQL).             |


### RESUMEN
| Concepto                     | Explicación                                                                               |
| ---------------------------- | ----------------------------------------------------------------------------------------- |
| **JDBC**                     | API estándar para conectar Java con bases de datos relacionales.                          |
| **Driver JDBC**              | Programa intermedio que traduce las órdenes Java a SQL comprensible para el SGBD.         |
| **Gestor de Drivers**        | Se encarga de localizar el driver apropiado según la URL de conexión.                     |
| **Tipos de Driver**          | Tipo I (Puente ODBC), Tipo II (Nativo), Tipo III (Protocolo de red), Tipo IV (Java puro). |
| **Bases de datos embebidas** | Motores de base de datos que se ejecutan dentro del propio programa (SQLite, H2).         |
| **Ventaja clave de JDBC**    | Independencia del SGBD; cambiar de base de datos sin cambiar el código Java.              |

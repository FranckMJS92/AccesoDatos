## Sección 2. Configuración e instalación de Hibernate

Hibernate es un framework ORM (Object-Relational Mapping) que permite trabajar con bases de datos relacionales utilizando objetos Java, sin necesidad de escribir continuamente SQL.
Para comenzar a utilizar Hibernate en un proyecto Java, necesitamos:

1. Descargar o declarar las librerías necesarias.

2. Configurar el acceso a la base de datos.

3. Definir las clases de dominio (beans/entidades).

4. Configurar los mapeos entre las clases y las tablas.

Lo habitual hoy día es no descargar manualmente los JARs, sino dejar que Maven o Gradle gestionen las dependencias.

### 2.1 Proyecto con Hibernate y MySQL

En esta sección se plantea un ejemplo clásico: queremos usar Hibernate en un proyecto Java que se conectará a una base de datos MySQL.

Hibernate, como framework, necesita dos dependencias principales:

1. El driver JDBC del SGBD (en este caso MySQL).

2. La librería Hibernate Core.

Dependencias en Maven (POM.XML)
```xml
<!-- Driver JDBC de MySQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.27</version>
</dependency>

<!-- Hibernate Core -->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-core</artifactId>
    <version>5.6.3.Final</version>
</dependency>
```
### 2.2 Estructura de un proyecto Hibernate

Una vez añadidas las dependencias, debemos conocer los tipos de archivos que conforman un proyecto típico de Hibernate. Este esquema es esencial y se repetirá siempre.

#### 1. BEAN (CLASES / ENTIDADES)

Las clases que mapean nuestros objetos de dominio.
Características:

- Atributos privados.
- Implementan la interfaz Serializable.
- Incluyen getters y setters públicos.
- Tienen constructor por defecto.

Ejemplo conceptual:
Un objeto de tipo Empleado → se representará en base de datos dentro de la tabla Empleado.

Estas clases son conocidas como POJO (Plain Old Java Objects) o beans.

#### 2. FICHEROS DE MAPEADO

Cada bean necesita un fichero donde se indica:

- A qué tabla corresponde.
- Qué columna corresponde a qué atributo.
- Si la clave primaria es autogenerada.
- Si hay relaciones con otras tablas.

Ejemplo típico:

- Clase Java → Empleado.java
- Su mapeo → Empleado.hbm.xml (hbm = Hibernate Mapping)

Más adelante veremos la sintaxis exacta de estos mapeos.

#### 3. CONFIGURACIÓN DE HIBERNATE

Hibernate puede configurarse de tres formas:

- Por código
- Por propiedades (.properties)
- Por XML (hibernate.cfg.xml) ← método más habitual y usado en esta asignatura.

El archivo hibernate.cfg.xml debe colocarse en la CLASSPATH del proyecto.

#### 4. RESTO DE CLASES Y APLICACIÓN

Una vez definidos beans, mapeos y configuración, necesitaremos:

- Clases de servicio
- Controladores
- Aplicación principal que inicia Hibernate
- Eventualmente vista (si es una app gráfica) o API

Hibernate se integra sin problemas con cualquier arquitectura en capas.

### 2.3 Configuración del proyecto

Para usar Hibernate de verdad, debemos realizar dos tareas esenciales:

Crear el archivo hibernate.cfg.xml con la configuración del SGBD.

Crear una clase auxiliar (HibernateUtil) que cargará la configuración y abrirá sesiones Hibernate.

#### Archivo hibernate.cfg.xml

Este archivo contiene:

1. Configuración de la base de datos

    - URL
    - Usuario
    - Contraseña
    - Driver JDBC

2. Activación de logs SQL:
```xml
<property name="show_sql">true</property>
```
3. Configuración del dialecto (traducción de Hibernate → SQL del SGBD)
```xml
<property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
```
4. Ficheros de mapeo:
```xml
<mapping resource="Empleado.hbm.xml"/>
```

#### Clase HibernateUtil.java

Es la clase encargada de:

- Cargar una única instancia de SessionFactory (patrón Singleton).
- Proporcionar una Session a las distintas partes de la aplicación.
- Habilitar transacciones y ejecución de consultas.

Sin esta clase Hibernate no puede funcionar, ya que es quien inicializa el framework

### RESUMEN
| Concepto          | Descripción                                               |
| ----------------- | --------------------------------------------------------- |
| Dependencias      | Librerías necesarias para usar Hibernate (driver + core). |
| Beans             | Clases Java que representan tablas.                       |
| Mapeos            | Archivos XML que enlazan clases ↔ tablas.                 |
| hibernate.cfg.xml | Archivo central de configuración.                         |
| HibernateUtil     | Clase que carga Hibernate y abre sesiones.                |


## Sección 3. 
### 3. 1. ¿Qué es el mapeo de objetos?

Un programa Java trabaja con objetos, con atributos, métodos, constructores…

Pero una base de datos relacional trabaja con tablas, filas y columnas.

Hibernate tiene como misión traducir entre ambos mundos.

Ejemplo sencillo:
| Clase Java     | Tabla SQL        |
| -------------- | ---------------- |
| `Peli`         | `Peli`           |
| campo `titulo` | columna `titulo` |
| campo `año`    | columna `año`   |

Una clase Java no se puede guardar sola en una base de datos, necesitamos decirle a Hibernate:

- Cómo se llama la tabla que representa a esta clase.
- Cuál es su clave primaria.
- Qué columna corresponde a qué atributo.

### 3. 2. Primera forma de mapear: Archivo de mapeo (.hbm.xml)

Esta fue la forma original de Hibernate.

Se crea:

- Una clase Java que representa la entidad, ej. Peli.java

- Un archivo XML que indica cómo se mapea, ej. Peli.hbm.xml

1. Ventajas
    - Muy separado de la lógica Java.
    - Fácil de modificar sin recompilar.

2. Inconvenientes

    - Hay que mantener DOS archivos.
    - Más verboso.

Ejemplo 
El XML indica:
```xml
<class name="Model.Peli" table="Peli">
    <id column="idPeli" name="idPeli" type="long">
        <generator class="native"/>
    </id>

    <property name="titulo" type="string" />
    <property name="año" type="integer"/>
    <property column="director" name="dDirector"/>
</class>
``` 
Este archivo dice:
- La clase Java se guarda en la tabla Peli.
- idPeli es la clave primaria.
- La columna director corresponde al atributo dDirector.

### 3. 3. Segunda forma: Mapeo por anotaciones JPA

Esta forma es la más moderna y utilizada hoy en día. Aquí no necesitamos archivo XML

Todo se coloca dentro de la clase Java usando anotaciones.

Ejemplo básico:
```java
@Entity
@Table(name="Peli")
public class Peli_Anotada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeli;

    @Column
    private String titulo;

    @Column
    private int anyo;

    @Column(name="director")
    private String dDirector;
}
```
Ventajas

- Una sola pieza de código: más limpio.
- Todo lo relativo al mapeo está junto a la clase.
- Es el estándar JPA → funciona con Hibernate, EclipseLink, etc.

Inconvenientes

- Si la clase está muy anotada puede quedar “ensuciada”.
- Modificar el mapeo exige recompilar.

### 3. 4. Componentes @Embedded / @Embeddable

Un componente es un objeto que NO será una tabla independiente.

Un ejemplo típico:
Una película en IMDb tiene nota + número de votos + URL del detalle.

Eso no merece otra tabla, solo un conjunto de valores.

Ejemplo del temario:
Clase "incrustada"
```java
@Embeddable
public class IMDB {
    @Column private String url;
    @Column private double nota;
    @Column private long votos;
}
``` 
Clase principal que lo contiene
```java
@Entity
public class Peli_Anotada {
    
    @Embedded
    private IMDB imdb;
}
```
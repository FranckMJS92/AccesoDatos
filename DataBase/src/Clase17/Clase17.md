## TEMA 6
### Sección 3. Consultas con MongoDB
#### 3.1 El comando find()

**Sintaxis básica**
```java
db.coleccion.find({ criterio_en_JSON })
```

MogoDB trabaja siempre con documentos JSON como criterio.
Ejemplo:

```java
db.alumnos.find({ edad: 20 })
```

Devuelve todos los documentos cuyo campo **edad** sea 20

**Obtener todos los documentos**
```java
db.alumnos.find({})
```
{} significa "sin filtro".

#### A. ESPECIFICAR CAMPOS A RECUPERAR
Por defecto, MongoDB devuelve el documento completo.

Podemos indicar qué campos queremos:
```java
db.alumnos.find(
  { edad: 20 },
  { nombre: 1, edad: 1 }
)
```
- 1 → mostrar
- 0 → ocultar

No se pueden mezclar 1 y 0 (excepto con _id).

Ejemplo ocultando _id:
```java
db.alumnos.find({}, { nombre: 1, _id: 0 })
```

B. OPERADORES DE COMPARACIÓN

MongoDB usa operadores con $.

Sintaxis general:
```java 
{ campo: { $operador: valor } }
```
| Operador | Significado   |
| -------- | ------------- |
| `$lt`    | menor que     |
| `$lte`   | menor o igual |
| `$gt`    | mayor que     |
| `$gte`   | mayor o igual |

Ejemplo:
```java
db.alumnos.find({ edad: { $gt: 18 } })
```
#### C. OPERADOR $OR

Permite combinar condiciones distintas:
```java
db.alumnos.find({
  $or: [
    { edad: 20 },
    { nombre: "Ana" }
  ]
})
```
#### D. OPERADORES $IN y $NIN

Buscar valores dentro de un conjunto:
```java
db.alumnos.find({
  edad: { $in: [18, 20, 22] }
})
```

Excluir valores:
```java
db.alumnos.find({
  edad: { $nin: [18, 20] }
})
```

#### E. OPERADOR $NOT

Invierte una condición:
```java
db.alumnos.find({
  edad: { $not: { $gt: 20 } }
})
```
#### F. OPERADOR $EXISTS

Comprobar si un campo existe:
```java
db.alumnos.find({
  telefono: { $exists: true }
})
```
#### 3.2 CONSIDERACIONES SOBRE TIPOS DE DATOS
**Valores nulos**

En MongoDB:

- { campo: null } devuelve:
    - documentos donde campo es null
    - documentos donde campo no existe

Para distinguir:
```java
db.alumnos.find({
  campo: { $exists: true, $eq: null }
})
```

**Expresiones regulares**

Muy importantes en MongoDB.

Forma JavaScript:
```java
db.alumnos.find({
  nombre: /Juan/
})
```

Forma con $regex:
```java
db.alumnos.find({
  nombre: { $regex: "Juan", $options: "i" }
})
```
Opciones:
| Opción | Significado          |
| ------ | -------------------- |
| i      | case-insensitive     |
| m      | multilínea           |
| s      | incluye saltos línea |
| x      | ignora espacios      |

**CONSULTAS CON ARRAYS**

Buscar valor dentro de un array

Si tenemos:
```java
{
  nombre: "Juan",
  asignaturas: ["BD", "Programacion"]
}
```
Consulta:
```java
db.alumnos.find({
  asignaturas: "BD"
})
```
- Devuelve documentos donde BD esté en el array.

**Operador $all**
```java
db.alumnos.find({
  asignaturas: { $all: ["BD", "Programacion"] }
})
```

**Operador $size**
```java
db.alumnos.find({
  asignaturas: { $size: 2 }
})
```
**$slice (proyección parcial del array)**
```java
db.alumnos.find(
  {},
  { asignaturas: { $slice: 1 } }
)
```

**DOCUMENTOS INCRUSTADOS**

Para acceder a claves internas usamos notación punto:
```java
db.peliculas.find({
  "director.nombre": "George"
})
```
#### 3.3 CURSORES

Cuando ejecutamos:
```java
db.alumnos.find()
```
MongoDB devuelve un cursor, no todos los datos de golpe.

El cliente itera sobre ese cursor.

LIMIT, SKIP Y SORT
- Limitar resultados
```java
db.alumnos.find().limit(5)
```
- Saltar resultados
```java
db.alumnos.find().skip(5)
```
- Ordenar

Ascendente:
```java
db.alumnos.find().sort({ edad: 1 })
```
Descendente:
```java
db.alumnos.find().sort({ edad: -1 })
```
#### 3.4 INTRODUCCIÓN AL AGGREGATION FRAMEWORK

Equivalente a GROUP BY, SUM, etc. en SQL.

Sintaxis:
```java
db.coleccion.aggregate([ pipeline ])
```
**Fases principales**
| Operador   | Función       |
| ---------- | ------------- |
| `$match`   | filtro        |
| `$group`   | agrupación    |
| `$project` | proyección    |
| `$sort`    | ordenar       |
| `$limit`   | limitar       |
| `$skip`    | saltar        |
| `$unwind`  | separar array |

**Ejemplo GROUP BY**

Agrupar alumnos por edad:
```java
db.alumnos.aggregate([
  {
    $group: {
      _id: "$edad",
      total: { $sum: 1 }
    }
  }
])
```

**Ejemplo con $match**
```java
db.alumnos.aggregate([
  { $match: { edad: { $gt: 18 } } },
  {
    $group: {
      _id: "$edad",
      total: { $sum: 1 }
    }
  }
])
```

**$unwind (arrays)**

Si un documento tiene:
```java
{
  nombre: "Juan",
  asignaturas: ["BD", "Programacion"]
}
```
```java
db.alumnos.aggregate([
  { $unwind: "$asignaturas" }
])
```
Genera un documento por cada elemento del array.

### RESUMEN GLOBAL DE LA SECCIÓN

En MongoDB podemos:

- Filtrar con find()
- Comparar con $gt, $lt, etc.
- Combinar condiciones con $or
- Trabajar con arrays ($all, $size)
- Usar expresiones regulares
- Limitar y ordenar resultados
- Realizar agregaciones tipo SQL

### Sección 4. MongoDB y Java
#### 4.1 DRIVERS
¿Qué es un driver?

Un driver es el componente que permite que una aplicación se comunique con la base de datos.

En SQL vimos:

- JDBC → Driver MySQL / PostgreSQL

En MongoDB:

- Driver oficial MongoDB

**Drivers disponibles**

MongoDB ofrece drivers para:
- C
- C++
- C#
- NodeJS
- Python
- Java
- Go
- etc.

Nos centramos en Java.

**Tipos de driver Java**

MongoDB ofrece:
- Driver síncrono (imperativo) ← el que vamos a usar
- Driver Reactive Streams (reactivo)

Usaremos el driver síncrono.

#### A. EL DRIVER DE JAVA

Este driver:
- Permite conectar a MongoDB local o remoto
- Se integra mediante Maven
- Proporciona clases e interfaces para trabajar con documentos

Dependencia típica en Maven:
```xml
<dependency>
  <groupId>org.mongodb</groupId>
  <artifactId>mongodb-driver-sync</artifactId>
</dependency>
```

#### B. CONEXIÓN A UNA BASE DE DATOS
- Clase principal: MongoClient

Equivalente conceptual:
| SQL        | MongoDB     |
| ---------- | ----------- |
| Connection | MongoClient |

**Crear conexión**

Ejemplo:
```java
String uri = "mongodb://localhost:27017";
MongoClient mongoClient = MongoClients.create(uri);
```
**¿Qué hace MongoClient?**

- Establece conexión con el servidor
- Es thread-safe
- Gestiona pool de conexiones

**Obtener base de datos**
```java
MongoDatabase database = mongoClient.getDatabase("miBD");
```
Aquí:
- No se crea físicamente hasta insertar datos
- Solo se obtiene referencia

**Métodos importantes de MongoClient**
| Método              | Función         |
| ------------------- | --------------- |
| getDatabase()       | Obtener BD      |
| listDatabaseNames() | Listar BDs      |
| close()             | Cerrar conexión |

#### MongoDatabase

Representa una base de datos concreta.

Equivalente conceptual:
| SQL           | MongoDB       |
| ------------- | ------------- |
| Base de datos | MongoDatabase |

**Métodos importantes**
| Método                | Función            |
| --------------------- | ------------------ |
| getCollection()       | Obtener colección  |
| listCollectionNames() | Listar colecciones |
| createCollection()    | Crear colección    |
| drop()                | Eliminar BD        |

#### C. CONSULTAS DESDE JAVA

Para trabajar con documentos:
```java
MongoCollection<Document> collection =
    database.getCollection("peliculas");
```
**Insertar documento**
```java
Document doc = new Document("titulo", "Matrix")
                    .append("anio", 1999);

collection.insertOne(doc);
```
**Buscar documentos**
```java
collection.find();
```
Pero normalmente usamos filtros.

**Clase Filters**

Permite construir consultas:
```java
collection.find(Filters.eq("anio", 1999));
```
Operadores equivalentes

| MongoDB Shell | Java Driver           |
| ------------- | --------------------- |
| {edad: 20}    | Filters.eq("edad",20) |
| $gt           | Filters.gt()          |
| $lt           | Filters.lt()          |
| $and          | Filters.and()         |
| $or           | Filters.or()          |

Ejemplo complejo
```java
collection.find(
    Filters.and(
        Filters.gt("anio", 2000),
        Filters.eq("plataforma", "PC")
    )
);
```
**Proyecciones en Java**

Clase Projections:
```java
collection.find()
    .projection(Projections.include("titulo", "anio"));
```

**AGREGACIONES EN JAVA**
```java
collection.aggregate(Arrays.asList(
    Aggregates.match(Filters.gt("precio", 20)),
    Aggregates.group("$plataforma",
        Accumulators.sum("total", 1))
));
```

#### 4.2 SPRING DATA MongoDB Y API REST

Aquí entramos en integración con Spring.

**¿Qué aporta Spring Data MongoDB?**
- Evita escribir código del driver manualmente
- Permite usar:
    - POJOs
    - Repositorios
    - Anotaciones

Es equivalente conceptual a:
| SQL             | MongoDB             |
| --------------- | ------------------- |
| Spring Data JPA | Spring Data MongoDB |

#### A. DEFINICIÓN DEL MODELO DOCUMENTO

En MongoDB no trabajamos con @Entity, sino con:
```java
@Document(collection = "videojuegos")
public class Videojuego {
```

**Campo ID**
```java
@Id
private String id;
```

Spring asigna automáticamente ObjectId si no lo indicamos.

**Diferencias respecto a JPA**
| JPA     | MongoDB              |
| ------- | -------------------- |
| @Entity | @Document            |
| @Table  | collection           |
| @Column | campo JSON           |
| JOIN    | documentos embebidos |


#### B. DEFINICIÓN DEL REPOSITORIO

Se usa:
```java
public interface VideojuegoRepository
       extends MongoRepository<Videojuego, String> {
}
```
MongoRepository ya incluye:
- findAll()
- findById()
- save()
- delete()

**Métodos personalizados**
```java
List<Videojuego> findByPlataforma(String plataforma);
```
Spring genera automáticamente la consulta.

**Consultas con @Query**
```java
@Query("{ precio: { $gt: ?0 } }")
List<Videojuego> findCaros(double precio);
```
#### C. DEFINICIÓN DEL SERVICIO

Muy similar a lo visto en JPA.
```java
@Service
public class VideojuegoServiceImpl implements VideojuegoService {

    @Autowired
    private VideojuegoRepository repository;

}
```

Responsabilidades:
- Lógica de negocio
- Validaciones
- Llamar al repositorio
- Enviar datos al controlador

#### D. DEFINICIÓN DEL CONTROLADOR

```java
@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {
```

| Anotación       | Función      |
| --------------- | ------------ |
| @RestController | API REST     |
| @RequestMapping | Ruta base    |
| @GetMapping     | GET          |
| @PostMapping    | POST         |
| @PutMapping     | PUT          |
| @DeleteMapping  | DELETE       |
| @PathVariable   | Variable URL |
| @RequestBody    | Recibir JSON |

**Flujo completo (arquitectura)**

Cliente HTTP
⬇
Controller
⬇
Service
⬇
Repository
⬇
MongoDB

#### DIFERENCIAS IMPORTANTES JPA vs MONGODB
| Aspecto    | JPA        | MongoDB         |
| ---------- | ---------- | --------------- |
| Modelo     | Relacional | Documental      |
| Relaciones | @OneToMany | Embebidos       |
| ID         | Long       | String/ObjectId |
| Consultas  | JPQL       | JSON            |
| Esquema    | Fijo       | Flexible        |

#### RESUMEN SECCIÓN
En esta seccón hemos visto:
- Qué es el driver MongoDB
- Cómo conectar desde Java
- Cómo usar MongoClient
- Cómo hacer consultas con Filters
- Cómo usar Spring Data MongoDB
- Cómo crear modelo, repositorio, servicio y controlador
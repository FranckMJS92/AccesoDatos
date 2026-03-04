## Tema 6.
### Sección 1. Bases de datos NoSQL
#### 1.1 El movimiento NoSQL

¿Por qué aparece NoSQL?

Hasta ahora, todo lo que hemos trabajado (JDBC, Hibernate, PostgreSQL…) se basa en el modelo relacional:

- Tablas
- Filas
- Columnas
- SQL

Este modelo funciona muy bien… pero tiene limitaciones.

**Problema principal: crecimiento de datos**

Con la llegada de la web moderna (Web 2.0 y 3.0):
- Redes sociales (Facebook, Twitter…)
- Aplicaciones globales
- Big Data
- IoT

Los datos crecen de forma exponencial. Esto provoca:

- Millones de usuarios simultáneos
- Grandes volúmenes de datos no estructurados
- Necesidad de alta velocidad y escalabilidad

**Limitaciones de las BBDD relacionales**

Las bases de datos relacionales empiezan a tener problemas en:

- Escalabilidad (no crecen bien horizontalmente)
- Rendimiento en grandes volúmenes
- Rigidez del esquema (tablas fijas)
- Relaciones complejas (JOINs costosos)

**¿Qué es NoSQL?**

NoSQL significa: “Not Only SQL” (No solo SQL)

No significa que no usen SQL, sino que:

- No siguen el modelo relacional clásico
- Usan otros modelos de almacenamiento
- Están diseñadas para grandes volúmenes de datos

**Idea clave**

NoSQL NO sustituye a las bases de datos relacionales. Es una alternativa para ciertos problemas

- Relacional → datos estructurados (empresa, facturación…)
- NoSQL → datos masivos o flexibles (apps, redes sociales…)

#### 1.2 Tipos de bases de datos NoSQL
Dentro del mundo NoSQL hay varios modelos. Vamos a ver los principales.

**A. BASES DE DATOS CLAVE–VALOR**
Cómo funcionan. Cada dato se guarda como:

clave → valor

- Ejemplo:

"usuario123" → {nombre: "Luis", edad: 30}

**Características**

- Muy rápidas
- Simples
- Sin relaciones

**B. BASES DE DATOS DOCUMENTALES**
Este es el modelo MÁS IMPORTANTE del tema. Cómo funcionan

Los datos se almacenan en documentos JSON. Cada documento puede tener estructura diferente

- Ejemplo
```json
{
  "titulo": "Star Wars",
  "anyo": 1977,
  "director": {
    "nombre": "George",
    "apellidos": "Lucas"
  }
}
```
**Características clave**

- Flexible (sin esquema fijo)
- Permite datos anidados
- Muy usada en aplicaciones web

Ejemplo real MongoDB

**Este es el modelo MÁS IMPORTANTE del tema.**


**C. BASES DE DATOS EN GRAFO**
Cómo funcionan. Datos representados como:

Nodos (entidades)

Aristas (relaciones)

- Ejemplo

Persona → amigo → otra persona

Usuario → sigue → usuario

- Uso típico
    - Redes sociales
    - Recomendadores
    - Relaciones complejas


#### 1.3 BASES DE DATOS DOCUMENTALES: MongoDB
**¿Qué es MongoDB?**

- Base de datos NoSQL documental
- Usa JSON (BSON internamente)
- Muy utilizada en aplicaciones modernas

**Equivalencia con modelo relacional**
| Relacional | MongoDB    |
| ---------- | ---------- |
| Tabla      | Colección  |
| Fila       | Documento  |
| Columna    | Campo JSON |

- Ejemplo práctico
Relacional: 
| id | titulo | director |
| -- | ------ | -------- |

MongoDB:
```json
{
  "_id": 1,
  "titulo": "La Amenaza Fantasma",
  "director": {
    "nombre": "George",
    "apellidos": "Lucas"
  }
}
```
Observa:
- El director está embebido
- No hace falta JOIN

**Idea importante**
En MongoDB:

- Los datos se agrupan
- Se evita dividir en muchas tablas

**CARACTERÍSTICAS DE MongoDB**
 1. Orientado a documentos

- Guarda objetos completos
- Se parece mucho a Java

 2. Esquema flexible

- Cada documento puede ser distinto
```json
{ "nombre": "Luis" }
{ "nombre": "Ana", "edad": 25 }
```

3. Alto rendimiento

- Menos JOINs
- Datos embebidos

 4. Escalabilidad

- Permite distribuir datos (sharding)

 5. Alta disponibilidad

- Replicación automática

 6. Menos necesidad de transacciones

- Operaciones atómicas por documento

**DIFERENCIAS IMPORTANTES**

- No usa SQL tradicional
- No hay JOINs (como en relacional)
- Usa consultas tipo JSON

#### 1.4 ECOSISTEMA MongoDB

MongoDB no es solo la base de datos, incluye:

- Versiones
    - Community (gratis)
    - Enterprise (empresas)

- MongoDB Atlas
    - Base de datos en la nube
    - Muy usado hoy en día

- Herramientas
    - MongoDB Compass (visual)
    - Drivers para Java, Python…

### Sección 2. Trabajar con MongoDB: operaciones básicas desde shell
#### 2.1 COLECCIONES Y DOCUMENTOS
**Concepto clave**

- En MongoDB:
    - Documento → equivalente a una fila (pero en JSON)
    - Colección → equivalente a una tabla
- Pero con una gran diferencia:
    - No hay esquema fijo (flexibilidad total)

**Características de los documentos**

**Sobre las claves**
- No pueden ser null
- Son case-sensitive
- Deben ser únicas dentro del documento
- No pueden contener $ ni .

**Sobre los valores**
- Pueden ser de cualquier tipo (número, string, array, objeto…)
- No hay restricciones rígidas como en SQL

**Sobre el documento**
- Siempre tiene un campo _id
- Si no lo defines → MongoDB lo crea automáticamente (ObjectId)

**Colecciones**
- Se crean automáticamente al insertar datos
- No tienen esquema fijo
- Documentos de la misma colección pueden ser distintos

#### 2.2 OPERACIONES BÁSICAS
**INSERT**
- insertOne()
```java
db.peliculas.insertOne({
  titulo: "Matrix",
  anio: 1999
})
```
- Inserta un documento
- Devuelve acknowledged: true

- insertMany()
```java
db.peliculas.insertMany([
  { titulo: "Star Wars", anio: 1977 },
  { titulo: "Avatar", anio: 2009 }
])
```
- Inserta varios documentos
- Devuelve lista de IDs

**SELECT (consultas)**
- find()
```java
db.peliculas.find()
```

- Devuelve todos los documentos

- find con filtro
```java
db.peliculas.find({ anio: 1999 })
```
- Filtra por condición

-  findOne()
```java
db.peliculas.findOne({ titulo: "Matrix" })
```
- Devuelve solo uno

**UPDATE**
- updateOne()
```java
db.peliculas.updateOne(
  { titulo: "Matrix" },
  { $set: { anio: 2000 } }
)
```
-  Actualiza un documento

- updateMany()
```java
db.peliculas.updateMany(
  {},
  { $inc: { visitas: 1 } }
)
```
- Actualiza varios

**DELETE**
- deleteOne()
```java
db.peliculas.deleteOne({ titulo: "Matrix" })
```
- deleteMany()
```java
db.peliculas.deleteMany({ anio: { $lt: 2000 } })
```

#### 2.3 TIPOS DE DATOS EN MONGODB
**Tipos básicos**
| Tipo     | Descripción  |
| -------- | ------------ |
| string   | Texto        |
| number   | Números      |
| boolean  | true / false |
| date     | Fecha        |
| array    | Lista        |
| object   | Documento    |
| ObjectId | ID único     |

**Importante: Date**
```java
let a = Date()
let b = new Date()
```
- Date() → string
- new Date() → objeto Date real

**Arrays (vectores)**
```java
let v = ["casa", 10, { texto: "hola" }, false]
```
Acceso:
```java
v[1]  // 10
```

**Documentos embebidos**
```java
{
  titulo: "Rogue One",
  director: {
    nombre: "Gareth",
    apellidos: "Edwards"
  }
}
```

**Muy importante:**
- Evita JOINs
- Datos relacionados en el mismo documento

**ObjectId**

Estructura interna:
- timestamp
- máquina
- proceso
- contador

Garantiza unicidad global

#### 2.4 INSERTAR DATOS (DETALLE)
**Consideraciones importantes**
- No necesitas cear la colección antes
- _id se enera automáticamente
- No hay esquema obligatorio

#### 2.5 ELIMINAR DOCUMENTOS
**deleteONE()**
```java
dc.pruebas.deleteOne({ x:1})
```

**deleteMany()**
```java
dc.pruebas.deleteMany({ x:{$gt:3 }})
```

**findOneAndDelete()**
```java
dc.pruebas.findOneAndDelete({ x:2})
```
Elimina y devuelve el documento

**CUIDADO**
```java
dc.pruebas.drop()
```
Elimina TODA la colección
#### 2.6 ACTUALIZACIÓN DE DOCUMENTOS
- REPLACE (reemplazo completo)
```Java
db.agenda.replaceOne(
  { nombre: "Jose" },
  { nombre: "Jose", telefono: "123" }
)
```
Sustituye TODO el documento

- UPDATE con modificadores
```java
db.agenda.updateOne(
  { nombre: "Jose" },
  { $set: { telefono: "999" } }
)
```
**Modificadores importantes**
| Operador | Función               |
| -------- | --------------------- |
| $set     | Cambia valor          |
| $unset   | Elimina campo         |
| $inc     | Incrementa            |
| $push    | Añade a array         |
| $pull    | Elimina de array      |
| $pop     | Elimina primer/último |


- UPSERT
```java
db.coleccion.updateOne(
  { nombre: "Ana" },
  { $set: { edad: 30 } },
  { upsert: true }
)
```
- si no existe -> lo crea
- Si eciste -> lo actualiza

### RESUMEN

- MongoDB trabaja con:
    - Colecciones (tablas)
    - Documentos JSON (filas)
- No hay esquema fijo -> gran flexibilidad
- Operaciones básicas:
    - insert
    - find
    - update
    - delete
- Se puede usar:
    - Arrays
    - Documentos embebidos
- No hay JOINs -> se modela diferente
- Uso de operadores ($set, $inc, etc)
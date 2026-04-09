# ¿Qué es el Aggregation Framework?

Es la forma que tiene MongoDB de hacer operaciones “avanzadas” sobre los datos.

## Idea clave:

Es el equivalente a hacer GROUP BY, SUM, COUNT, filtros y transformaciones en SQL.

Forma de entenderlo fácil

**En SQL harías:**
```sql
SELECT genero, COUNT(*)
FROM videojuegos
GROUP BY genero;
```
En MongoDB se hace con:
```JavaScript
db.videojuegos.aggregate([
  {
    $group: {
      _id: "$genero",
      total: { $sum: 1 }
    }
  }
])
```

**Concepto clave: Pipeline**

MongoDB trabaja en etapas (pipeline).

Explicación:

Es como una cadena de pasos, donde cada paso transforma los datos.

Ejemplo visual:
```
Datos → $match → $group → $sort → Resultado final
```

Etapas más importantes
1. $match → FILTRO (como WHERE)
```JavaScript
{
  $match: { precio: { $gt: 50 } }
}
```
Filtra documentos

2. $group → AGRUPAR (como GROUP BY)
```JavaScript
{
  $group: {
    _id: "$genero",
    total: { $sum: 1 }
  }
}
```
Agrupa por campo

3. $project → SELECCIONAR CAMPOS
```JavaScript
{
  $project: {
    titulo: 1,
    precio: 1
  }
}
```
Elegir qué mostrar

4. $sort → ORDENAR
```JavaScript
{
  $sort: { precio: -1 }
}
```
5. $limit → LIMITAR
```JavaScript
{
  $limit: 5
}
```
6. $unwind → ARRAYS (MUY IMPORTANTE)

 Convierte arrays en filas

## Ejemplo completo

Colección:
```JavaScript
db.videojuegos.insertMany([
  { titulo: "Zelda", genero: "Aventura", precio: 60 },
  { titulo: "Mario", genero: "Aventura", precio: 50 },
  { titulo: "FIFA", genero: "Deportes", precio: 70 }
])
```
### Ejercicio: contar juegos por género
```JavaScript
db.videojuegos.aggregate([
  {
    $group: {
      _id: "$genero",
      total: { $sum: 1 }
    }
  }
])
```
Resultado:
```json
[
  { "_id": "Aventura", "total": 2 },
  { "_id": "Deportes", "total": 1 }
]
```
### Ejercicio 2: solo juegos caros y agrupar
```JavaScript
db.videojuegos.aggregate([
  {
    $match: { precio: { $gt: 50 } }
  },
  {
    $group: {
      _id: "$genero",
      total: { $sum: 1 }
    }
  }
])
```

El Aggregation Framework permite procesar documentos mediante una tubería de etapas (pipeline), similar a las consultas avanzadas de SQL como GROUP BY y funciones de agregación.
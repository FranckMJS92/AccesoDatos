Coleccion Videojuegos.
```java
db.videojuegos.insertMany([
  { titulo: "Zelda", plataforma: "Switch", precio: 60, puntuacion: 9.7 },
  { titulo: "Elden Ring", plataforma: "PC", precio: 50, puntuacion: 9.5 },
  { titulo: "Minecraft", plataforma: "Multi", precio: 25, puntuacion: 9.0 }
])`
```

Consultas:
- Juegos con precio > 40
- Juegos con puntuación >= 9.5
- Juegos Switch o PC
- Juegos cuyo título contenga "Ring"
- Ordenar por precio descendente
- Agrupar por plataforma y contar
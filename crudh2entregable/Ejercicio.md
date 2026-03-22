# EJERCICIO 1
## Objetivo
Crear un CRUD REST completo con Spring Boot conectado a H2, siguiendo la misma estructura que el proyecto ***películas***, pero ahora con **videojuegos** : Model -> Repository -> Service -> ServiceImpl -> Controller.

## 1. Enunciado.
Desarrollar una API REST para gestionar **videojuegos**

La API debe permitir:
1. **CREAR** un videojuego.
2. **LISTAR** todos los videojuegos.
3. **BUSCAR** un videojuego por id.
4. **ACTUALIZAR** un videojuego (por id).
5. **ELIMINAR** un videojuego (por id).
6. **FILTRAR** videojuegos por **plataforma** y por **puntuación mínima** (dos diferentes endpoint)

## 2. Guion paso a paso
### Paso 1. Crear Proyecto
- Project: Maven
- Group: es.ejercicio
- Artifact: videojuegos-api
- Dependencias:
    - Spring Web
    - Spring Data JPA
    - H2 Database

### Paso 2. Estructura por capas
Dentro de src/main/java/...
- controller
- model
- repositoy
- service
    - serviceImpl
(BDVideojuegos)

### Paso 3. Modelo de datos
Campos mínimos: Entidad Videodejuegos.
- id -> tipo Long
- titulo -> String
- platadorma -> String
- genero -> String 
- anio -> int 
- precio -> double -> >=0
- puntuacion -> double -> 0-10

### Paso 4. Repository
```java
List<Videojuegos> findByPlataforma(String plataforma);
List<Videojuego> findByPuntuacionGreaterThanEqual(double puntacion);
``` 


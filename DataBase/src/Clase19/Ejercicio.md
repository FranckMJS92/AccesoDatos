# EJERCICIO 1
Desarrollar una API REST con Spring Boot conectada a una base de datos MongoDB, para gestionar un catálogo de libros.

Cada libro será almacenado como un documento JSON dentro de una colección de MongoDB.

## Datos del libro
de cada libro se almacenará:

- id (generado automáticamente)
- titulo
- autor
- genero (Novela, Ciencia ficción, Fantasía, Historia...)
- precio
- disponible (true/ false)
- paginas

## Objetivo

Crear una API REST que permita:

- Gestionar libros (CRUD completo)
- Realizar consultas filtradas
- Visualizar los datos en MongoDB Compass

Además, se deberán implementar filtros por distintos campos.

## Requisitos obligatorios

Debes usar:

- Spring Boot
- MongoDB
- Arquitectura por capas:
    - model
    - repository
    - service
    - serviceImpl
    - controller
    - Anotaciones:
        - @Document
        - @Id
        - @RestController
        - @Service
- MongoRepository
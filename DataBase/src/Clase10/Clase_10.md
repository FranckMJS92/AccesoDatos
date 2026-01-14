## TEMA 4. Bases de datos orientadas a objetos
### Seccion 1. Introducción. El modeo de datos ODMG
### 1.1 Evolucion de los SGBD
El punto de partida será el modelo relacionaol.
Los Sistemas Gestores de Vases de DAtos (SGBDR) se basan en:
- Tablas
- Filas (registros)
- Columnas (atributos)
- Relaciones mediante claves primarias (PR) y claves externas (FK)


Este modelo nos proporciona:
- Robustez
- Normalizado
- Representa muy bien una visión estructurada y estática de los datos.

Presenta un problema cuando se combina con programación orientada a objetos.

El desface objeto-relacional:
En programación orientada a objetos trabajamos con:
- Objetos
- Atributos complejos
- Colecciones
- Herencias

Pero en el modelo relacional:
- Todo se descompone en tablas
- Los objetos complejos se dividen en varias entidades
- LAs relaciones se gestionan con FK

Consecuencia del desface:
- Aumento del número de tablas
- Aumento del número de relaciones
- Mayor complejidad
- Mayor número de restricciones

Esto se intentaba soluciona con los ORM como Hibernate

### 1.2 BDOO - Bases de Datos Orientadas a Objetos.
En una BDOO, los datos se almacenan como objetos persistentes, no como filas de tablas.

Características fundamentales.

Conceptos;
Identificación de Objetos (OIB) -> Cada objeto tiene un identificador único independiente de sus estado
Encapsulamiento -> Los datos se acceden mediante métodos, no directamente.
Persistencia -> Los objetos sobreviven al fina del programa.
Navegavilidad -> Se accede a otros objetos mediante referencias, no medianete JOIN.
Herencia -> Se pueden crear objetos a partir de otros.
Identidad -> Dos objetos son distintos aunque tenggan los mismos valores.
Consulta sencilla -> Se utilizan lenguajes orientados a objetos (OQL)

Diferencia conceptual:
- BD relacional
    * Persona -> idPersona
    * Direccion -> idDireccion
    FK entre tablas

- BDOO
    * Persona -> Direccion (objeto)

### 1.3 Implementacion del estandar ODMG
ODMG es la consolidación del estándar para evitar que cada fabricante implemente su propia solución

Componentes del estándar ODMG
Elemento:
Modelo de datos -> Define cómo se representan los objetos
ODL (Object DEfinition Language) -> Lenguaje para definir clases persistentes
OGL (Object Query Language) -> Lenguaje de consultas orientadas a objetos


### 1.4 BDOR - Bases de DAtos Objeto-Relacionales
Las BDOR son una solución intermedia entre:
- Bases de datos relaciones tradicionales
- Bases de datos orientadas a objetos puros

No abandonan el modelo relacional, pero lo amplian.

Características principales de las BDOR:
- Soporte para tipos definidos por el usuario
- Almacenamiento de estructuras complejas
- Arrays y coleciones
- Almacenamiento de objetos
- Soporte parcial de herencia
- Funciones asociadas a tipo

IMPORTANTE -> Internamente siguen siendo tablas, pero con capacidades extras.

### RESUMEN
- El modelo relacional no desaparece.
- El modelo orienteado a objetos no lo sustituye
- Las BDOO y BDOR surgen para:
    * Reducir el desface objeto-tradicional
    * Facilitar el desarrollo OO
    * Mantener persistencia y transacciones

ORM no es lo mismo que BDD, el ORM es un puente y BDOO es un cambio.
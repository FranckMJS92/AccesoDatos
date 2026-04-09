1. ¿Qué es un fichero binario en Java?

A) Un fichero que solo contiene texto

B) Un fichero que almacena datos en formato legible

C) Un fichero que almacena datos en formato no legible directamente

D) Un fichero XML

✅ Respuesta correcta: C

Explicación:
Los ficheros binarios almacenan datos en bytes. No están pensados para ser leídos directamente por humanos, sino por programas.

2. ¿Qué clase se usa para escribir datos primitivos en binario?

A) FileWriter

B) BufferedWriter

C) DataOutputStream

D) PrintWriter

✅ Respuesta correcta: C

Explicación:
DataOutputStream permite escribir tipos primitivos como int, double, boolean, etc., en formato binario.

3. (Verdadero/Falso) Es obligatorio leer un fichero binario en el mismo orden en que se escribió.

✅ Respuesta correcta: Verdadero

Explicación:
Si no se respeta el orden de lectura, los datos se interpretan incorrectamente (por ejemplo, leer un double donde había un int).

4. ¿Qué es Serializable en Java?

A) Permite imprimir objetos

B) Convierte objetos en bytes

C) Permite ejecutar código

D) Convierte JSON en Java

✅ Respuesta correcta: B

Explicación:
La interfaz Serializable permite convertir objetos en una secuencia de bytes para guardarlos o enviarlos.

5. ¿Qué es JDBC?

A) Un lenguaje SQL

B) Un sistema gestor de bases de datos

C) Una API para conectar Java con bases de datos

D) Un servidor

✅ Respuesta correcta: C

Explicación:
JDBC es una API que permite a programas Java conectarse y operar con bases de datos relacionales.

6. ¿Qué clase gestiona las conexiones en JDBC?

A) Connection

B) DriverManager

C) Statement

D) ResultSet

✅ Respuesta correcta: B

Explicación:
DriverManager se encarga de localizar el driver adecuado y crear la conexión.

7. (Verdadero/Falso) Connection representa una conexión activa a la base de datos.

✅ Respuesta correcta: Verdadero

Explicación:
Connection es el objeto que representa una sesión activa con la base de datos.

8. ¿Qué método se usa para ejecutar consultas SELECT?

A) executeUpdate()

B) executeQuery()

C) executeInsert()

D) execute()

✅ Respuesta correcta: B

Explicación:
executeQuery() se usa para SELECT y devuelve un ResultSet.

9. ¿Qué devuelve un ResultSet?

A) Un archivo

B) Una tabla de resultados

C) Un String

D) Un objeto

✅ Respuesta correcta: B

Explicación:
ResultSet contiene los resultados de una consulta en forma de filas y columnas.

10. ¿Qué es una URL JDBC?

A) Dirección web

B) Cadena de conexión a la base de datos

C) Un driver

D) Un archivo

✅ Respuesta correcta: B

Explicación:
La URL JDBC indica cómo conectarse (tipo BD, host, puerto, base de datos, etc.).

11. ¿Qué es Hibernate?

A) Un SGBD

B) Un lenguaje

C) Un ORM

D) Un driver

✅ Respuesta correcta: C

Explicación:
Hibernate es un ORM que mapea objetos Java a tablas relacionales.

12. ¿Qué problema resuelve Hibernate?

A) Problema de red

B) Problema de memoria

C) Desfase objeto-relacional

D) Problema de compilación

✅ Respuesta correcta: C

Explicación:
Evita tener que convertir manualmente entre objetos Java y tablas SQL.

13. ¿Qué anotación define una entidad en JPA?

A) @Table

B) @Entity

C) @Object

D) @Class

✅ Respuesta correcta: B

Explicación:
@Entity indica que una clase se mapea a una tabla.

14. ¿Qué anotación marca la clave primaria?

A) @Column

B) @Primary

C) @Id

D) @Key

✅ Respuesta correcta: C

Explicación:
@Id identifica el campo que actúa como clave primaria.

15. ¿Qué tipos de relaciones existen en JPA?

A) OneToOne

B) OneToMany

C) ManyToMany

D) Todas las anteriores

✅ Respuesta correcta: D

Explicación:
JPA soporta todos esos tipos de relaciones.

16. ¿Qué es MongoDB?

A) Base de datos relacional

B) Base de datos documental

C) Lenguaje

D) API

✅ Respuesta correcta: B

Explicación:
MongoDB es una base de datos NoSQL orientada a documentos.

17. ¿Cómo se almacenan los datos en MongoDB?

A) Tablas

B) Filas

C) Documentos JSON

D) XML

✅ Respuesta correcta: C

Explicación:
MongoDB usa documentos tipo JSON (internamente BSON).

18. ¿Qué método se usa para consultar en MongoDB?

A) select()

B) find()

C) query()

D) get()

✅ Respuesta correcta: B

Explicación:
find() se usa para recuperar documentos.

19. (Verdadero/Falso) MongoDB necesita un esquema fijo.

❌ Respuesta correcta: Falso

Explicación:
MongoDB es flexible y no requiere esquema definido.

20. ¿Qué es MongoRepository?

A) Clase JDBC

B) Interfaz de Spring Data

C) Driver

D) JSON

✅ Respuesta correcta: B

Explicación:
MongoRepository proporciona métodos CRUD automáticamente.

21. ¿Qué anotación sustituye a @Entity en MongoDB?

A) @Table

B) @Document

C) @Json

D) @Mongo

✅ Respuesta correcta: B

Explicación:
@Document indica que la clase se almacena en una colección.

22. ¿Qué hace @RestController?

A) Define BD

B) Define API REST

C) Define modelo

D) Define servicio

✅ Respuesta correcta: B

Explicación:
Marca la clase como controlador REST.

23. ¿Qué hace @GetMapping?

A) Insertar

B) Eliminar

C) Consultar

D) Actualizar

✅ Respuesta correcta: C

Explicación:
Se usa para peticiones GET.

24. ¿Qué es un Service?

A) BD

B) Lógica de negocio

C) Controlador

D) JSON

✅ Respuesta correcta: B

Explicación:
El Service contiene la lógica de negocio.

25. ¿Qué es una API REST?

A) Programa de escritorio

B) Interfaz HTTP para acceder a datos

C) BD

D) Driver

✅ Respuesta correcta: B

26. ¿Qué es el Aggregation Framework?

A) CRUD

B) Sistema de logs

C) Sistema de análisis de datos

D) Driver

✅ Respuesta correcta: C

Explicación:
Permite agrupar, filtrar y analizar datos.

27. ¿Qué operador agrupa datos en MongoDB?

A) $match

B) $group

C) $sort

D) $find

✅ Respuesta correcta: B

28. ¿Cómo esta representada una BD orientada a objetos?

A) Tablas

B) Objetos directamente

C) JSON

D) XML

✅ Respuesta correcta: B

29. ¿Qué es una BD objeto-relacional?

A) Solo objetos

B) Solo tablas

C) Mezcla de ambas

D) JSON

✅ Respuesta correcta: C

30. ¿Qué capas tiene una arquitectura Spring?

A) Controller

B) Service

C) Repository

D) Todas

✅ Respuesta correcta: D

Explicación:
Spring organiza el código en capas para separar responsabilidades.
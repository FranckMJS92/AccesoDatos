## Sección 4. Metainformación de la base de datos
### 4.1 Objeto ResultSet
Un ResultSet representa la tabla de resultados que devuelve una consulta SQL, normalmente un SELECT.

Ejemplo gráfico simplificado:
| cursor          | Col1 | Col2 | Col3 |
| --------------- | ---- | ---- | ---- |
| `next()` → true | dato | dato | dato |

El ResultSet siempre funciona con un cursor, que empieza antes de la primera fila.

* Método clave: next()

El cursor avanza de fila en fila:

next() devuelve true si hay otra fila disponible.

next() devuelve false cuando no quedan filas.

* ¿Cómo se leen los datos?

Una vez situados en una fila con next(), los métodos para leer columnas son:

| Método           | Qué recupera    |
| ---------------- | --------------- |
| `getInt(pos)`    | Entero          |
| `getString(pos)` | Texto           |
| `getDouble(pos)` | Número real     |
| `getObject(pos)` | Objeto genérico |

También se pueden leer columnas por nombre:
```java
rs.getString("nombre");
rs.getInt("edad");
```

Errores habituales
- Intentar acceder a columnas antes de hacer next().
- Usar un nombre de columna incorrecto.
- Acceder después de cerrar el ResultSet.
- Saltarse filas por hacer más de un next() dentro del bucle.

¿Cómo hacerlo de forma correcta?
```java
ResultSet rs = st.executeQuery("SELECT * FROM JUEGO");

while (rs.next()) {
    int id = rs.getInt("ID");
    String nombre = rs.getString("NOMBRE");
    double puntuacion = rs.getDouble("PUNTUACION");
}
```

### 4.2. Metadatos de la base de datos (DatabaseMetaData)

Los metadatos son información sobre la estructura de la base de datos:

- Qué tablas existen
- Qué columnas tiene cada tabla
- Cuáles son claves primarias
- Relaciones entre tablas
- Propiedades del SGBD

Esto se obtiene a través de:

```java
DatabaseMetaData dbmd = conexion.getMetaData();
```

Principales métodos de DatabaseMetaData
* Información general del SGBD

| Método                     | Devuelve                 |
| -------------------------- | ------------------------ |
| `getDatabaseProductName()` | Nombre del SGBD          |
| `getDriverName()`          | Nombre del driver JDBC   |
| `getURL()`                 | URL usada en la conexión |
| `getUserName()`            | Usuario conectado        |

#### Listar tablas existentes
```java
dbmd.getTables(catalogo, esquema, nombreTabla, tipos);
```
Devuelve un ResultSet con las tablas del sistema.

Parámetros:

- catalogo → normalmente null
- esquema → null o "PUBLIC" en H2
- nombreTabla → patrón (ej: "JUEGO" o "%")
- tipos → array de tipos: "TABLE", "VIEW"…

#### Listar columnas de una tabla
```java
dbmd.getColumns(null, null, "JUEGO", null);
```
Devuelve un ResultSet con todas las columnas de la tabla JUEGO.
Campos útiles:

- COLUMN_NAME
- TYPE_NAME
- COLUMN_SIZE

#### Claves primarias
```java
dbmd.getPrimaryKeys(null, null, "JUEGO");
```
Devuelve un ResultSet con los campos que son clave primaria.

#### Claves ajenas (foreign keys)
Claves que apuntan a otra tabla:
```java
dbmd.getImportedKeys(null, null, "TABLA");
```

Claves desde otras tablas que apuntan a esta tabla:
```java
dbmd.getExportedKeys(null, null, "TABLA");
```

### RESUMEN
| Concepto              | Explicación                                                |
| --------------------- | ---------------------------------------------------------- |
| **ResultSet**         | Objeto devuelto por un SELECT. Se recorre con `next()`.    |
| **Cursor**            | Posición actual dentro del ResultSet.                      |
| **getXXX()**          | Métodos para leer columnas por índice o nombre.            |
| **DatabaseMetaData**  | Interfaz para consultar la estructura de la base de datos. |
| **getTables()**       | Lista las tablas del esquema.                              |
| **getColumns()**      | Lista columnas de una tabla.                               |
| **getPrimaryKeys()**  | Devuelve campos PK.                                        |
| **getImportedKeys()** | Foreign keys que llegan a la tabla.                        |
| **getExportedKeys()** | Foreign keys que salen desde la tabla.                     |


## Sección 5. Consultas a la Base de Datos
### 5.1. Operaciones sobre la base de datos

Antes de poder recuperar o modificar información en una base de datos, debemos crear y ejecutar una sentencia SQL desde Java.
Para ello, utilizamos tres clases fundamentales del API JDBC:
| Clase JDBC            | Función                                                   |
| --------------------- | --------------------------------------------------------- |
| **Statement**         | Ejecuta sentencias SQL fijas (sin parámetros).            |
| **PreparedStatement** | Ejecuta sentencias SQL parametrizadas (con `?`).          |
| **ResultSet**         | Representa la tabla de resultados que devuelve un SELECT. |

Existen dos tipos principales de ejecución:
| Método              | Uso                                                           |
| ------------------- | ------------------------------------------------------------- |
| **executeQuery()**  | Para sentencias que retornan resultados (SELECT).             |
| **executeUpdate()** | Para sentencias que modifican datos (INSERT, UPDATE, DELETE). |

## 5.2. Tipos de sentencias
### Sentencias SQL Fijas

Son sentencias constantes, es decir, no dependen de variables externas.
Ejemplo típico: consultar todos los datos de una tabla.
```java
String SQL = "SELECT * FROM Juego";  
Statement st = conn.createStatement();  
ResultSet rst = st.executeQuery(SQL);

while (rst.next()) {  
    System.out.println(rst.getString("NOMBRE") +  
        " - " + rst.getString("GENERO") +
        " - " + rst.getDouble("PUNTUACION"));
}
```
Comentarios clave:

- executeQuery() ejecuta la sentencia y devuelve un ResultSet.
- El método next() desplaza el cursor hacia adelante y devuelve true si hay más filas.
- Para leer cada columna usamos métodos getXXX según el tipo de dato:
getInt(), getString(), getDouble(), etc.

Ejemplo de modificación (UPDATE o INSERT)
```java
String SQL = "INSERT INTO Juego (NOMBRE, GENERO, PUNTUACION) " +
             "VALUES ('Portal 2','Puzzles', 9.3)";

Statement st = conn.createStatement();
int filas = st.executeUpdate(SQL);

if (filas == 1)
    System.out.println("Inserción realizada con éxito");
else
    System.out.println("Error en la inserción");

```
executeUpdate() devuelve el número de filas afectadas.

### Sentencias Variables

Cuando la sentencia necesita valores dinámicos, NO debemos usar concatenación directa, porque:

- Es inseguro (riesgo de SQL Injection)
- Es difícil de mantener
- Se rompe si hay comillas dentro de los strings

Ejemplo incorrecto (NUNCA usar en producción):
```java
String nombre = "Zelda";  
String SQL = "SELECT * FROM Juego WHERE NOMBRE='" + nombre + "'";
```

### Sentencias Preparadas (PreparedStatement)

Este es el método seguro y profesional.

Usa placeholders (?) para evitar problemas de inyección SQL.

Ejemplo:
```java
String SQL = "SELECT * FROM Juego WHERE ID = ?";
PreparedStatement pst = conn.prepareStatement(SQL);
pst.setInt(1, 3); // primer ?

ResultSet rst = pst.executeQuery();
```

- Evita SQL Injection
- El driver valida el tipo automáticamente
- Más eficientes si se ejecutan muchas veces

Insert con PreparedStatement
```java
String SQL = "INSERT INTO Juego(NOMBRE, GENERO, PUNTUACION) VALUES (?, ?, ?)";
PreparedStatement pst = conn.prepareStatement(SQL);

pst.setString(1, "Hades");
pst.setString(2, "Roguelike");
pst.setDouble(3, 9.5);

int filas = pst.executeUpdate();
```

### Metadatos de las Consultas

JDBC permite obtener información sobre la consulta, como columnas y tipos.

Para ello usamos:
```java
ResultSetMetaData meta = rst.getMetaData();
```

Métodos útiles:

| Método                 | Devuelve                      |
| ---------------------- | ----------------------------- |
| `getColumnCount()`     | Número de columnas del SELECT |
| `getColumnName(i)`     | Nombre de la columna i        |
| `getColumnTypeName(i)` | Tipo SQL de la columna i      |


Ejemplo:
```java
ResultSetMetaData meta = rst.getMetaData();
int columnas = meta.getColumnCount();

for (int i = 1; i <= columnas; i++) {
    System.out.println(meta.getColumnName(i) + " - " +
                       meta.getColumnTypeName(i));
}
```

Esto es clave para consultas dinámicas o herramientas de depuración.

## 5.3. Scripts SQL

Un script SQL es un archivo con múltiples sentencias.
Para ejecutarlo:

1. Leer el archivo línea a línea
2. Unirlo todo en una sola cadena
3. Ejecutarlo con executeUpdate()

Se debe activar:
```java
st.execute(scriptSQL);
```

## 5.4. Transacciones

Por defecto, cada sentencia SQL se ejecuta independientemente.
Para asegurar integridad, podemos abrir una transacción:
```java
conn.setAutoCommit(false);
```

Confirmar:
```java
conn.commit();
```

Cancelar cambios:
```java
conn.rollback();
```

## 5.5. ResultSet Actualizables

Permiten modificar datos directamente desde el ResultSet.

Para crearlos:
```java
Statement st = conn.createStatement(
        ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE);
```

Esto permite:

- Borrar filas:
```java
rst.deleteRow();
```
- Modificar columnas:
```java
rst.updateString("GENERO", "Acción");
rst.updateRow();
```

- Insertar nuevas filas:
```java
rst.moveToInsertRow();
rst.updateString("NOMBRE", "Nuevo juego");
rst.insertRow();
```


### Resumen de la sección
| Tema                       | Qué es                                                |
| -------------------------- | ----------------------------------------------------- |
| **Sentencias fijas**       | SQL sin parámetros                                    |
| **Sentencias variables**   | SQL construido con variables (NO recomendable)        |
| **Sentencias preparadas**  | SQL profesional con placeholders `?`                  |
| **ResultSet**              | Tabla devuelta por un SELECT                          |
| **Metadatos**              | Información sobre columnas                            |
| **Transacciones**          | Controlar bloques de operaciones                      |
| **ResultSet actualizable** | Permite modificar, borrar e insertar datos desde Java |

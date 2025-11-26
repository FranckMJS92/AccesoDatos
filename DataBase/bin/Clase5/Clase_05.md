## Sección 3. Conexión a la base de datos
### 3.1 Establecimiento de la conexión

Para conectarnos a una base de datos, Java utiliza la clase DriverManager, encargada de gestionar los drivers JDBC que hay cargados en memoria.
La aplicación necesita tres datos básicos:
| Parámetro         | Descripción                                                                                           |
| ----------------- | ----------------------------------------------------------------------------------------------------- |
| **HOST**          | Dirección IP o nombre del equipo donde está la base de datos. Ejemplo: `localhost` o `192.168.1.100`. |
| **PUERTO**        | Puerto de escucha del SGBD. Ejemplo: `3306` para MySQL, `5432` para PostgreSQL.                       |
| **BASE DE DATOS** | Nombre del esquema o base de datos a la que queremos conectarnos. Ejemplo: `BDJuegos`.                |

Además, el driver JDBC requiere conocer el protocolo y tipo de base de datos para saber cómo interpretar la conexión.
Por ello, se construye una cadena de conexión (URL JDBC) con esta estructura:
```
jdbc:<tipo_de_bd>://<host>:<puerto>/<nombre_bd>
```

Por ejemplo, para MySQL:
```java
String connectionUrl = "jdbc:mysql://localhost:3306/BDJuegos";
```

Carga del driver

Antes de conectarnos, debemos cargar el driver JDBC que se encargará de traducir las peticiones. Esto se hace normalmente así:
```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

Esto indica a Java qué clase se encargará de manejar la conexión con MySQL.

#### Establecimiento de la conexión

Para crear la conexión, se usa el método getConnection() de DriverManager, que tiene varias versiones:
| Método                                                    | Descripción                                                                              |
| --------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| `getConnection(String url)`                               | Conecta usando solo la URL (si el usuario y contraseña están incluidos en la URL).       |
| `getConnection(String url, String user, String password)` | Conecta usando la URL y credenciales aparte.                                             |
| `getConnection(String url, Properties info)`              | Conecta usando un objeto con múltiples propiedades (usuario, clave, codificación, etc.). |

```java
String connectionUrl = "jdbc:mysql://localhost:3306/BDJuegos";
Connection conn = DriverManager.getConnection(connectionUrl, "root", "toor");
```

Esto establece una conexión con la base de datos BDJuegos que está en localhost, puerto 3306, con usuario root y contraseña toor.

### 3.2. Parámetros de la conexión

En la URL podemos añadir más información, por ejemplo usuario y contraseña directamente:
```java
String connectionUrl = "jdbc:mysql://localhost:3306/BDJuegos?user=root&password=toor";
Connection conn = DriverManager.getConnection(connectionUrl);
```

Esto es equivalente al ejemplo anterior, pero con las credenciales incluidas dentro de la URL.

Además, podemos añadir otros parámetros opcionales separados por &, como el juego de caracteres o propiedades del driver.
Por ejemplo:
```java
String connectionUrl = 
    "jdbc:mysql://localhost:3306/BDJuegos?user=root&password=toor&useUnicode=true&characterEncoding=UTF-8";
Connection conn = DriverManager.getConnection(connectionUrl);
```
#### Explicación de los parámetros adicionales:
| Parámetro                 | Significado                                                               |
| ------------------------- | ------------------------------------------------------------------------- |
| `useUnicode=true`         | Indica que se usarán caracteres Unicode (importante para acentos o eñes). |
| `characterEncoding=UTF-8` | Define la codificación de caracteres usada en la comunicación.            |
| `serverTimezone=UTC`      | (Opcional) Define la zona horaria para evitar errores de tiempo.          |


#### Hard-coded: un error común

En muchos ejemplos se colocan los datos de conexión directamente en el código fuente.
Esto se llama hard-coded, y es una mala práctica porque:

* Si cambian los datos (usuario, contraseña o servidor), hay que modificar el código y recompilar.

* Es un riesgo de seguridad, ya que las contraseñas quedan visibles.

Solución: guardar los datos en un archivo de configuración o en variables externas.

Ejemplo parametrizado:
```java
String usuario = "root";
String passwd = "toor";
String dbName = "BDJuegos";

String connectionUrl = "jdbc:mysql://localhost:3306/" + dbName +
                       "?user=" + usuario +
                       "&password=" + passwd +
                       "&useUnicode=true&characterEncoding=UTF-8";

Connection conn = DriverManager.getConnection(connectionUrl);
```
### 3.3. Organizar y centralizar la conexión

Cuando una aplicación necesita acceder a la base de datos muchas veces, conviene centralizar la lógica de conexión en una sola clase.
Así evitamos duplicar código y reducimos errores al abrir o cerrar conexiones.

Ejemplo de una clase ConexionBD que encapsula toda la lógica:
```java
public class ConexionBD {

    private Connection laConexion = null;  // Variable que guarda la conexión activa

    // Método privado que crea la conexión (solo se invoca desde dentro)
    private void connect() {
        try {
            if (laConexion == null) {
                String url = "jdbc:mysql://localhost:3306/BDJuegos";
                laConexion = DriverManager.getConnection(url, "root", "toor");
                System.out.println("Conexión establecida correctamente.");
            }
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    // Método para cerrar la conexión
    public void disconnect() {
        try {
            if (laConexion != null) {
                laConexion.close();
                System.out.println("Conexión cerrada correctamente.");
                laConexion = null;
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    // Método público que devuelve la conexión (crea una si no existe)
    public Connection getConnection() {
        if (laConexion == null) {
            connect(); // Si no existe, la crea
        }
        return laConexion;
    }
}
```

## Resumen
| Concepto                          | Explicación                                                                          |
| --------------------------------- | ------------------------------------------------------------------------------------ |
| **Driver JDBC**                   | Librería que traduce las órdenes SQL de Java al lenguaje del SGBD.                   |
| **DriverManager**                 | Clase que gestiona los drivers registrados y crea las conexiones.                    |
| **Connection**                    | Objeto que representa una conexión activa con la base de datos.                      |
| **Cadena de conexión (URL JDBC)** | Texto que indica protocolo, host, puerto, base de datos y propiedades.               |
| **Hard-coded**                    | Mala práctica de incluir credenciales directamente en el código.                     |

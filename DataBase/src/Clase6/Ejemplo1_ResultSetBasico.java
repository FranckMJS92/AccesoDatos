package Clase6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Ejemplo 1: Lectura de los datos usando ResultSet
// Relacionado con 4.1: Objeto ResultSet

public class Ejemplo1_ResultSetBasico {

    public static void main(String[] args) {
        // 1. Datos de conexión (Igual que la Clase 05)
        String url = "jdbc:h2:./Clase_05/BDJuegos"; // Ajutar la ruta de vuestra BBDD (BDJueos en modo fichero)
        String user = "sa"; // Usuario por defecto de H2
        String password = ""; // Contraseña vacía

        try {
            // 2. Cargar el driver H2 (org.h2.driver)
            Class.forName("org.h2.Driver");
            System.out.println("Driver H2 cargado correctamente.");
            // 3. Establecer la conexión con la base de datos
            // URL + Credenciales separadas
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida con BDJuegos.");

            // 4. Crear un Statement para ejecutar una consulta
            // El Statement es el "canal" por donde enviamos el SQL al motor
            Statement st = conn.createStatement();

            // 5. Definir la sentencia SQL que vamos a ejecutar
            String sql = "SELECT ID, NOMBRE, GENERO, PUNTUACION FROM JUEGO";

            // 6. Ejecutar la consulta y obtener el ResultSet
            // El ResultSet es una "tabla" que nos va a devlver la BD, con tantas filas como
            // registros tenga.
            ResultSet rs = st.executeQuery(sql);

            // 7. Recorrer el ResultSet con rs.next()

            System.out.println("Lista de juegos (usando ResultSet):");

            while (rs.next()) {
                // 7.1 Leer columnas con el getXXX()

                // - getInt() -> leer un entero
                // - getString() -> leer un texto
                // - getDouble() -> leer un número real
                int id = rs.getInt("ID"); // Columna ID
                String nombre = rs.getString("NOMBRE"); // Columna Nombre
                String genero = rs.getString("GENERO"); // Columna Genero
                double puntuacion = rs.getDouble("PUNTUACION"); // Columna Puntuacion

                // 7.2 Mostrar la fila
                System.out.println(" - [" + id + "] " + nombre +
                        " (" + genero + ") -> " + puntuacion);
            }

            // 8. Cerramos recursos en orden inverso de apertura
            rs.close(); // Cerramos primero el ResultSet
            st.close(); // Cerramos el Statement
            conn.close(); // Cerramos la conexión
            System.out.println("Conexión cerrada correctamente.");

        } catch (ClassNotFoundException e) {
            // Error al cargar el driver
            System.out.println("Driver H2 no encontrado: " + e.getMessage());

        } catch (SQLException e) {
            // Error con SQL (URL mala, tabla inexitente (Sergio),...)
            System.out.println("Error SQL: " + e.getMessage());
        }
    }
}

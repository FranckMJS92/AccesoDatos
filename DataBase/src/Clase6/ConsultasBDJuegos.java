package Clase6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

// Ejemplo de varias consultas sobre la base de datos BDJuegos 
// Usaremos Statement y PreparedStatement
public class ConsultasBDJuegos {

    // 1. Datos de la conexión
    private static final String URL = "jdbc:h2:./src/Clase5/BDJuegos";

    // usuario por defecto de H2
    private static final String USER = "sa";
    private static final String PASS = "";

    // MAIN
    public static void main(String[] args) {
        System.out.println("=== EJEMPLOS DE CONSULTAS SOBRE BDJuegos ===");

        try {
            // 1. Cargar la clase del driver H2
            Class.forName("org.h2.Driver");
            System.out.println("Driver H2 cargado.");

            // 2. Obtener una coneción a la base de datos
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.println("Conexión establecida con BDJuegos.\n");

                // Llamamos a varios ejemplos de consultas
                consultaTodosLosJuegos(conn); // Sentencia FIJA (SELECT *)
                consultaJuegosPorGenero(conn, "Aventura"); // Sentencia Preparada (SELECT con WHERE)
                insertarNuevoJuego(conn, "God of War", "Acción", 10); // INSERT Preparado
                actualizarPuntuacion(conn, "Minecraft", 9.3); // UPDATE preparado

                // Mostrar de nuevo todo el contenido después de los cambios.
                System.out.println("\nDespués de insertar y actualizar:");
                consultaTodosLosJuegos(conn);

            }

            System.out.println("\nConexión cerrada. Fin del programa.");

        } catch (ClassNotFoundException e) {
            System.out.println("Error: no se ha encontrado el driver H2 -> " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error SQL -> " + e.getMessage());
        }
    }

    // EJEMPLO 1: SETENCIA FIJA CON STATEMENT (SELECT *)
    private static void consultaTodosLosJuegos(Connection conn) throws SQLException {
        System.out.println(">>> Consulta 1: listar todos los juegos");

        // 1. Creamos la sentencia SQL FIJA (no tiene parámetros)
        String sql = "SELECT ID, NOMBRE, GENERO, PUNTUACION FROM JUEGO";

        // 2. Creamos un Statement a partir de la conexión
        try (Statement st = conn.createStatement();

                // 3. Ejecutamos la consulta, como es un SELECT, usamos executeQuery()
                ResultSet rs = st.executeQuery(sql)) {

            // 4. Recorrer el resultSet
            // next() avanza fila a fila.
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String genero = rs.getString("GENERO");
                double puntuacion = rs.getDouble("PUNTUACION");

                System.out.println("   [" + id + "] " + nombre +
                        " (" + genero + ") -> " + puntuacion);
            }
        } // Se cierra rs y st automáticamene
    }

    // EJEMPLO 2: SENTENCIA PREPARADA (SELECT filtrando por género)
    private static void consultaJuegosPorGenero(Connection conn, String generoBuscado) throws SQLException {
        System.out.println("\n>>> Consulta 2: listar juegos del género = " + generoBuscado);

        // 1. SQL con placeholder (?) donde irá el valor del género
        String sql = "SELECT ID, NOMBRE, GENERO, PUNTUACION FROM JUEGO WHERE GENERO = ?";
        // 2. Creamos un PreparedStatement a partir d ela conexión
        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // 3. Asignamos valores a los placeholders
            // El primer ? es el índice1, el segundo sería el índice 2,...
            pst.setString(1, generoBuscado);

            // 4. Ejecutamos la consulta preparada
            try (ResultSet rs = pst.executeQuery()) {

                // 5. Recorremos el ResultSet igual que antes.
                boolean hayResultados = false;
                while (rs.next()) {
                    hayResultados = true;
                    int id = rs.getInt("ID");
                    String nombre = rs.getString("NOMBRE");
                    String genero = rs.getString("GENERO");
                    double puntuacion = rs.getDouble("PUNTUACION");

                    System.out.println("   [" + id + "] " + nombre +
                            " (" + genero + ") -> " + puntuacion);
                }

                if (!hayResultados) {
                    System.out.println("   (No hay juegos con ese género)");
                }
            }
        }
    }

    // EJEMPLO 3: INSERT CON PREPAREDSTATEMENT (executeUpdate)
    private static void insertarNuevoJuego(Connection conn,
            String nombre,
            String genero,
            double puntuacion) throws SQLException {
        System.out.println("\n>>> Consulta 3: insertar nuevo juego");

        // 1. SQL de inserccion con placeholders
        String sql = """
                INSERT INTO JUEGO (NOMBRE, GENERO, PUNTUACION)
                VALUES (?, ?, ?)
                """;

        // 2. Creamos PreparedStatement
        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // 3. Asignamos cada parámetro con setXXX(posición, valor)
            pst.setString(1, nombre);
            pst.setString(2, genero);
            pst.setDouble(3, puntuacion);

            // 4. Ejecutamos la sentencia
            // como es un ISERT/UPDATE/DELETE utilizamos executeUpdate()
            int filas = pst.executeUpdate();

            // 5. Comprobamos cuántas dilas se han insertado.
            if (filas == 1) {
                System.out.println("   Inserción correcta: " + nombre +
                        " (" + genero + ") -> " + puntuacion);
            } else {
                System.out.println("  Inserción no realizada (filas afectadas: " + filas + ")");
            }
        }
    }

    // EJEMPLO 4: UPDATE CON PREPAREDSTATEMENT
    private static void actualizarPuntuacion(Connection conn,
            String nombreJuego,
            double nuevaPuntuacion) throws SQLException {
        System.out.println("\n>>> Consulta 4: actualizar puntuación");
        System.out.println("   Juego = " + nombreJuego + ", nueva puntuación = " + nuevaPuntuacion);

        // 1. SQL de actualización con placeholders
        String sql = """
                UPDATE JUEGO
                SET PUNTUACION = ?
                WHERE NOMBRE = ?
                """;
        // 2. Creamos PreparedStatement
        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // 3. Asignamos valores: primero puntuación y después el nombre
            pst.setDouble(1, nuevaPuntuacion);
            pst.setString(2, nombreJuego);

            // 4. Ejecutamos la actuañización
            int filas = pst.executeUpdate();

            // 5. Comprobamos cuántas filas se han modificado
            if (filas == 0) {
                System.out.println("   No se ha encontrado ningún juego con ese nombre.");
            } else {
                System.out.println("   Puntuación actualizada en " + filas + " fila(s).");
            }
        }
    }
}

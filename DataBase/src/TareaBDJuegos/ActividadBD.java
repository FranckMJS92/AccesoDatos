package TareaBDJuegos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ActividadBD {
    // 1. Datos de la conexión
    private static final String URL = "jdbc:h2:./src/TareaBDJuegos/BDJuegos";

    // usuario por defecto de H2
    private static final String USER = "sa";
    private static final String PASS = "";

    public static void main(String[] args) {
        // Objeto Scanner para leer datos de teclado
        Scanner scan = new Scanner(System.in);
        byte option = 0;

        try {

            Class.forName("org.h2.Driver");
            System.out.println("---------------------");
            System.out.println("| Driver H2 cargado |");
            System.out.println("---------------------");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.print("Conectado a la Base de datos BDJuegos.\n\n");

                do {
                    System.out.println("\n=== MENU CONSULTA BASE DE DATOS JUEGOS ===");
                    System.out.println("1. Consultar y mostrar todos los juegos");
                    System.out.println("2. Insertar un nuevo juego");
                    System.out.println("3. Actualizar la puntuación de un juego");
                    System.out.println("4. Borrar un juego (por nombre o ID");
                    System.out.println("5. Salir");

                    // Validar que la entrada sea un número
                    boolean entradaValida = false;
                    while (!entradaValida) {
                        System.out.print("Seleccione una opción (1-5): ");

                        if (scan.hasNextByte()) {
                            option = scan.nextByte();
                            entradaValida = true;

                        } else {
                            System.out.println("¡Error! Debe ingresar un número válido.");
                            scan.next(); // Limpiar el buffer
                        }
                    }

                    // Procesar la opción válida
                    switch (option) {
                        case 1:
                            System.out.println("\nConsultando todos los juegos...");
                            consultaSelect(conn);
                            break;
                        case 2:
                            System.out.println("\nInsertando nuevo juego...");
                            insertarNuevoJuego(conn, "Minecraft", "Acción", 10);
                            break;
                        case 3:
                            System.out.println("\nActualizando puntuación...");
                            actualizarPuntuacion(conn, "Minecraft", 5.3);
                            break;
                        case 4:
                            System.out.println("\nEliminando juego...");
                            eliminarJuego(conn, 3);
                            break;
                        case 5:
                            System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }

                } while (option != 5);
            }
        } catch (ClassNotFoundException e) {
            System.out.print("Driver H2 no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.print(" Error SQL: " + e.getMessage());
        }

    }

    // METODO 1: SELECT * FROM JUEGOS
    private static void consultaSelect(Connection conn) throws SQLException {

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

    // METODO 2: INSERT INTO
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

    // METODO 3: UPDATE JUEGOS SET
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

    // MÉTODO 4. DELETE FROM JUEGOS WHERE NAME "borrarJuegoPorNombre"
    private static void eliminarJuego(Connection conn, int ID) throws SQLException {
        // 1. SQL de actualización con placeholders
        String sql = """
                DELETE FROM JUEGO
                WHERE ID = ?
                """;

        // 2. Creamos PreparedStatement
        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            // 3. Asignamos valores: primero puntuación y después el nombre
            pst.setInt(1, ID);

            // 4. Ejecutamos la actuañización
            int filas = pst.executeUpdate();

            // 5. Comprobamos cuántas filas se han modificado
            if (filas == 0) {
                System.out.println("   No se ha encontrado ninguna fila con ese ID.");
            } else {
                System.out.println("   Puntuación actualizada en " + filas + " fila(s).");
            }
        }
    }
}

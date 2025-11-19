package Clase5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class InitBDJuegos {
    public static void main(String[] args) {

        String url = "jdbc:h2:./src/Clase5/BDJuegos";
        String user = "sa";
        String password = "";

        try {
            Class.forName("org.h2.Driver");
            System.out.println("Driver H2 cargado.");

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado a BDJuegos.");

            Statement st = conn.createStatement();

            // 1) Crear tabla si no existe
            String crearTabla = """
                    CREATE TABLE IF NOT EXISTS JUEGO (
                        ID INT AUTO_INCREMENT PRIMARY KEY,
                        NOMBRE   VARCHAR(100),
                        GENERO   VARCHAR(50),
                        PUNTUACION DOUBLE
                    );
                    """;
            st.execute(crearTabla);
            System.out.println("Tabla JUEGO preparada.");

            // 2) Insertar algunos datos de ejemplo
            String insertar1 = """
                    INSERT INTO JUEGO (NOMBRE, GENERO, PUNTUACION)
                    VALUES ('The Legend of Zelda', 'Aventura', 9.7);
                    """;

            String insertar2 = """
                    INSERT INTO JUEGO (NOMBRE, GENERO, PUNTUACION)
                    VALUES ('FIFA', 'Deportes', 9.0);
                    """;

            // IMPORTANTE: en un ejemplo real convendría comprobar duplicados o usar IF NOT
            // EXISTS.
            st.executeUpdate(insertar1);
            st.executeUpdate(insertar2);
            System.out.println("Datos de ejemplo insertados.");

            // 3) Consultar los juegos y mostrarlos por pantalla
            String consulta = "SELECT ID, NOMBRE, GENERO, PUNTUACION FROM JUEGO";
            ResultSet rs = st.executeQuery(consulta);

            System.out.println("Lista de juegos en la BD:");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                String genero = rs.getString("GENERO");
                double puntuacion = rs.getDouble("PUNTUACION");

                System.out.println(" - [" + id + "] " + nombre +
                        " (" + genero + ") -> " + puntuacion);
            }

            // 4) Cerrar recursos
            rs.close();
            st.close();
            conn.close();
            System.out.println("Conexión cerrada.");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver H2 no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }
    }
}

package Clase_06;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActividadCRUD_Juegos {

    //1. Datos de la conexión
    private static final String URL  = "jdbc:h2:./Clase_05/BDJuegos";

    // usuario por defecto de H2 
    private static final String USER = "sa";
    private static final String PASS = "";
    public static void main(String[] args) {
        
        try {
            
            Class.forName("org.h2.Driver");
            System.out.print("Driver H2 cargado.");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.print("Conectado a la Base de datos BDJuegos.\n");

                // 1. Llamamos al método de consulta "listarJuegos"
                // 2. Llamamos al método de insertat "insertarJuego"
                // 3. Llamamos al método de consulta "listarJuegos"
                // 4. Llamamos al método de actualizar "actualizarPuntuancion"
                // 5. Llamamos al método de consulta "listarJuegos"
                // 6. Llamamos al método de eliminar "borrarJuegoPorNombre"
                // 7. Llamamos al método de consulta "listarJuegos"
            }
        } catch (ClassNotFoundException e){
            System.out.print("Driver H2 no encontrado: " + e.getMessage());
        } catch (SQLException e){
            System.out.print(" Error SQL: " + e.getMessage());
        }
    }
    // MÉTODO 1. "listarJuegos"
    // MÉTODO 2. "insertarJuego"
    // MÉTODO 3. "actualizarPuntuacion"
    // MÉTODO 4. "borrarJuegoPorNombre"
}

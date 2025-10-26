import java.io.*;

public class LeerCSV {
    public static void main(String[] args) {
        String ruta = "alumnos.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String cabecera = br.readLine();
            String[] columnas = cabecera.split(",");

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                for (int i = 0; i < campos.length; i++) {
                    System.out.println(columnas[i] + ": " + campos[i] + "");
                }
                System.out.println();
                /*
                 * System.out.println("\nNombre: " + campos[0]
                 * + "\nEdad: " + campos[1]
                 * + "\nCiclo: " + campos[2]
                 * + "\nNota: " + campos[3]);
                 */
            }
        } catch (IOException e) {
            System.err.println("Error al leer CSv: " + e.getMessage());
        }
    }
}

package Clase1;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LeerLineas {

    public static void main(String[] args) {
        String ruta = "texto.txt"; // fichero de texto de la carpeta
        File f = new File(ruta); // Creamos el objeto File
        try {
            FileReader fr = new FileReader(f); // Creamos el lector
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Linea leida: " + linea);
            }
            br.close();// Cerrar BufferedReader y FileReader
        } catch (IOException e) {
            System.out.println("Erros al leer el fichero: " + e.getMessage());
        }
    }

}

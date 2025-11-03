package Clase1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LeerCaracteres {
    public static void main(String[] args) {
        String ruta = "text.txt"; // fichero de texto de la carpeta
        File f = new File(ruta); // Creamos el objeto File
        try {
            FileReader fr = new FileReader(f); // Creamos el lector
            int c;
            while ((c = fr.read()) != -1) {
                char letra = (char) c; // Convertir el numero a caracter
                System.out.println(letra); // Mostrar el caracter
            }
            fr.close(); // Cerrar el flujo de lectura
        } catch (IOException e) {
            System.out.println("Erros al leer el fichero: " + e.getMessage());
        }

    }
}

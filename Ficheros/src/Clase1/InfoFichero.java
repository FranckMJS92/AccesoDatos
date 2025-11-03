package Clase1;
import java.io.File;

public class InfoFichero {
    public static void main(String[] args) {
        // Ruta Fichero
        String ruta = "C:\\Users\\HP\\Downloads";

        File f = new File(ruta);

        if (f.exists()) {
            System.out.println("El elemento existe, es un EXITO!");

            if (f.isFile()) {
                System.out.println("Es un fichero, OLE!");
                System.out.println("Nombre: " + f.getName());
                System.out.println("Ruta Absoluta: " + f.getAbsolutePath());
                System.out.println("Tamaño: " + f.length() + "bytes");
            }

            if (f.isDirectory()) {
                System.out.println("es un directorio, OHHH");
                String[] contenido = f.list();
                for (String nombre : contenido) {
                    System.out.println(" - " + nombre);
                }
            }

        } else {
            System.out.println("El fichero o directorio no existe!");
        }
    }
}

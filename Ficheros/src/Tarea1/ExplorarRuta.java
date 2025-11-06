package Tarea1;

import java.io.*;

public class ExplorarRuta {
    public static void main(String[] args) {
        String ruta = "C:\\Users\\HP\\Downloads";

        // Se crea el objeto file con el argumento ingresado por usuario
        File f = new File(ruta);

        if (f.exists()) {

            if (f.isFile()) {
                System.out.println("Nombre : " + f.getName());
                System.out.println("Tamaño: " + f.length() + " bytes");
                if (f.canRead() && f.canWrite()) {
                    System.out.println("El archivo se puede leer y escribir");
                } else
                    System.out.println("Archivo no se puede leer o escribir");
            }

            if (f.isDirectory()) {
                String contenido[] = f.list();
                for (String nombre : contenido) {
                    System.out.println(nombre);
                }

                System.out.println("El directorio tiene : " + contenido.length + " elementos");

            }

        } else
            System.out.println("La ruta no existe");

    }
}

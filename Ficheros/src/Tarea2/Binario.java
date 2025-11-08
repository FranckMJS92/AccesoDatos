package Tarea2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Binario {
    public static void main(String[] args) {
        String ruta = "C:\\Users\\HP\\GithubRepository\\AccesoDatos\\Ficheros\\src\\Tarea2\\Tarea2.bin";

        // Guardar variables: int - String - double
        try (DataOutputStream n = new DataOutputStream(new FileOutputStream(ruta))) {
            n.writeInt(73);
            n.writeUTF("Cadena de texto para guardar");
            n.writeDouble(1.1235);

            System.out.println("Datos escritos en : " + ruta);

        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        // Leer variables guardadas anteriormente
        try (DataInputStream m = new DataInputStream(new FileInputStream(ruta))) {

            int i = m.readInt();
            String s = m.readUTF();
            double d = m.readDouble();

            System.out.printf("%30s\n %s\n %-20s %d\n %-20s %s\n %-20s %-20.2f", "Datos Binarios",
                    "-------------------------------------------------",
                    "Variable entera",
                    i, "Variable texto", s,
                    "Variable decimal", d);

        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

}

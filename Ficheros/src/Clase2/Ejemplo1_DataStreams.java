package Clase2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Ejemplo1_DataStreams {

    public static void main(String[] args) {
        String ruta = "ej1.bin";

        // ESCRITURA: Guardamos un int -> String -> double
        try (DataOutputStream n = new DataOutputStream(new FileOutputStream(ruta))) {
            n.writeInt(10);
            n.writeUTF("Estoy feliz");
            n.writeDouble(3.14);

            System.out.println("Datos escritos en : " + ruta);

        } catch (IOException e) {
            System.out.println("Error de escritura: " + e.getMessage());
        }

        // LECTURA
        try (DataInputStream m = new DataInputStream(new FileInputStream(ruta))) {

            // IMPORTANTE: tenemos que leer en el mis o ornde en el que se ha escrito
            int i = m.readInt();
            String s = m.readUTF();
            double d = m.readDouble();

            System.out.println("--- Leido ---"
                    + "\ni = " + i
                    + "\ns = " + s
                    + "\nd = " + d);
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }

}

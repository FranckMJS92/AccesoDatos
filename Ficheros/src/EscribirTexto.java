import java.io.*;

public class EscribirTexto {

    public static void main(String[] args) {
        String ruta = "texto.txt";
        try {
            FileWriter fw = new FileWriter(ruta, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Primera linea de textxo");
            bw.newLine();
            bw.write("Segunda linea de textxo");
            bw.newLine();
            bw.write("Tercera linea de textxo");
            bw.newLine();
            bw.close();
            System.out.println("fichero escrito correctamente.");
        } catch (Exception e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }
}

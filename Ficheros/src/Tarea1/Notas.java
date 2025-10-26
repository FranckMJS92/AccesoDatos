package Tarea1;

import java.util.Scanner;
import java.io.*;

public class Notas {
    public static void main(String[] args) {
        String f1, f2, f3;
        byte i = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("A continuacion se le pedirá ingrese 3 frases ...");

        System.out.print("Primera frase: ");
        f1 = sc.nextLine();
        System.out.print("Segunda frase: ");
        f2 = sc.nextLine();
        System.out.print("Tercera frase: ");
        f3 = sc.nextLine();

        // Try Catch para escritura de archivo
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("frases.txt"));
            bw.write(f1);
            bw.newLine();
            bw.write(f2);
            bw.newLine();
            bw.write(f3);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir archivo " + e.getMessage());
        }

        // Try Catch para lectura de archivo
        try {
            BufferedReader br = new BufferedReader(new FileReader("frases.txt"));
            String linea;
            while ((linea = br.readLine()) != null) {
                i++;
                System.out.println(i + "\t" + linea);
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error al leer archivo : " + e.getMessage());
        }

        sc.close();

    }
}

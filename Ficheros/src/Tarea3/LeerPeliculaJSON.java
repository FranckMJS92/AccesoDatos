package Tarea3;

import org.json.*;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LeerPeliculaJSON {
    public static void main(String[] args) {
        // Paso 1. List donde recostruiremos los objetos leídos del JSON.
        List<Pelicula> pelis = new ArrayList<>();

        // Paso 2. Intentamos abrir el archivo 'curso.json' con el FileReader (lee
        // caracteres).
        try (FileReader fr = new FileReader(
                "C:\\Users\\HP\\GithubRepository\\AccesoDatos\\Ficheros\\src\\Tarea3\\peliculas.json")) {

            // Paso 3. Creamo un JSONTokener apartir del Reader (convierte texto en tokens
            // JSON).
            JSONTokener tokener = new JSONTokener(fr);

            // Paso 4. Contruir el objeto JSON raíz con el tokener
            JSONObject root = new JSONObject(tokener);

            // Paso 6. Obtener el array 'curso' (utilizamos el getJSONArray)
            JSONArray arrPeliculas = root.getJSONArray("pelis");

            // Paso 7. Recorremos el array 'curso' y para cada posicion tomamos el objeto
            // JSON del módulo.
            for (int i = 0; i < arrPeliculas.length(); i++) {
                JSONObject oPelicula = arrPeliculas.getJSONObject(i); // objeto del módulo en la posición i

                // Paso 8. Extraemos campos con getXxx
                String titulo = oPelicula.getString("titulo");
                String director = oPelicula.getString("director");
                int anio = oPelicula.getInt("año");

                // Paso 9. Recostruir el objeto Java y lo añadimos a la lista
                Pelicula p = new Pelicula(titulo, director, anio);
                pelis.add(p);

                // Mostramos por consola lo que hemos leido
                System.out.printf("%-20s %-20s %-20d\n",titulo,director,anio);
            }

            // Mostramos el tamaño de los módulos leídos
            System.out.println("Total peliculas leídas: " + pelis.size());

        } catch (FileNotFoundException e) {
            System.out.println("No encuentro 'peliculas.json'");
        } catch (JSONException e) {
            System.out.println("JSON mal formado o clave/valor inesperado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }
}

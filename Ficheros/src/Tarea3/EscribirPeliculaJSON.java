package Tarea3;

import org.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EscribirPeliculaJSON {
    public static void main(String[] args) {
        // Paso 1. Preparar datos
        List<Pelicula> pelis = Arrays.asList(
                new Pelicula("Tron: Legacy", "Steven Lisberger", 2010),
                new Pelicula("Barbie", "Greta Gerwig", 2023),
                new Pelicula("Baby Driver", "Edgar Wright", 2017));

        // Paso 2. Creamos el objeto JSON raiz donde vamos a incorporar todo el
        // contenido
        JSONObject root = new JSONObject();

        // Paso 3. Añadimos campos pares clave-valor
        root.put("pelis", "Buenas");

        // Paso 4. Creamos un JSONArray para la lista de modulos
        JSONArray arrPeliculas = new JSONArray();

        // Paso 5. Recorremos a lista de objetos y convertimos cada uno en JSONObject
        for (Pelicula p : pelis) {
            // Creamos un objeto JSON
            JSONObject oPelicula = new JSONObject();
            // Copiamos dentro de los campos del objeto
            oPelicula.put("titulo", p.getTitulo()); // {"nombre":"Acceso a Datos"}
            oPelicula.put("director", p.getDirector()); // {"nombre":"Acceso a Datos", "horas":6}
            oPelicula.put("año", p.getAnio()); // {"nombre":"Acceso a Datos", "horas":6, "nota":8.45}
            // Añadimos el objeto del modulo del array JSON
            arrPeliculas.put(oPelicula);
        }
        // Paso 6. Colgamos el array del modulo en la clave "curso"
        root.put("pelis", arrPeliculas); // {"ciclo":"DAM", "año":2, "curso":[...]}

        // Paso 7. Escribimos el JSON a un archivo físico.
        try (FileWriter fw = new FileWriter(
                "C:\\Users\\HP\\GithubRepository\\AccesoDatos\\Ficheros\\src\\Tarea3\\peliculas.json")) { // Crear el
                                                                                                      // 'curso.json'
            fw.write(root.toString(4)); // Convertir el JSONObject a texto
            System.out.println("JSON escrito en curso.json");
        } catch (IOException e) {
            System.out.println("Error escribiendo JSON: " + e.getMessage());
        }

        // Paso 8. (OPCIONAL) Mostar por pantalla el contenido del JSON
        System.out.println("\n--- JSON generado ---");
        System.out.println(root.toString(2));
    }
}

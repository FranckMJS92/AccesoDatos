package Clase2; // Paquete del proyecto 

import javax.xml.parsers.*; // Para construir documentos DOM (DocumentBuilder, Factory)
import javax.xml.transform.*; // Para transformar el DOM a fichero (Transformer)
import javax.xml.transform.dom.DOMSource; // Fuente de datos: el árbol DOM
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;

public class EscribirXML {
    public static void main(String[] args) {
        try {
            // Paso 1. Crear un documento DOM vacío
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // A partir de la fábrica, pedimos un "constructor" de documentos
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Creamos un Document vacío (aún no tiene nodos)
            Document doc = dBuilder.newDocument();

            // Paso 2. Crear elemento raíz <curso>
            Element root = doc.createElement("curso");
            // Añadimos atributos a la etiqueta raíz: nivel="2" y ciclo="DAM"
            root.setAttribute("nivel", "2");
            root.setAttribute("ciclo", "DAM");
            // Insertamos el nodo raíz dentro del Document
            doc.appendChild(root);

            // Paso 3. Crear primer módulo. Creamos un elemento <modulo> y lo colgamos del
            // nodo raíz
            Element modulo = doc.createElement("modulo");
            root.appendChild(modulo);

            // Creamos la etiqueta <nombre>
            Element nombre = doc.createElement("nombre");
            // Creamos el nodo de texto "Acceso a Datos" y lo metemos dentro de <nombre>
            nombre.appendChild(doc.createTextNode("Acceso a Datos"));
            // Colgamos <nombre> dentro de <modulo>
            modulo.appendChild(nombre);

            // Subnodo <horas>
            Element horas = doc.createElement("horas");
            horas.appendChild(doc.createTextNode("6")); // El texto siempre va como String
            modulo.appendChild(horas);

            // Subnodo <nota>
            Element nota = doc.createElement("nota");
            nota.appendChild(doc.createTextNode("8.45"));
            modulo.appendChild(nota);

            // Paso 4. Guardar el documento XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // Propiedad de salida: INDENT "yes" para que el XML quede legible
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // El DOMSource envuelve nuestro Document (fuente de la transformación)
            DOMSource source = new DOMSource(doc);
            // El StreamResult indica a dónde escribir (a un fichero en este caso)
            StreamResult result = new StreamResult(new File("C:\\Users\\HP\\GithubRepository\\AccesoDatos\\Ficheros\\src\\Clase2\\nuevoCurso.xml"));

            // Ejecutamos la transformación: del DOM en memoria → al fichero físico
            transformer.transform(source, result);

            // Mensaje final de confirmación
            System.out.println("Archivo XML creado correctamente.");

        } catch (Exception e) {
            // Cualquier error de configuración del parser/transformer o de E/S llegará aquí
            System.out.println("Error al crear XML: " + e.getMessage());
            // e.printStackTrace(); // Útil en depuración para ver la traza completa
        }
    }
}

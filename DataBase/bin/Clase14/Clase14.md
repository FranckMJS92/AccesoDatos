## TEMA 5
### Sección 3. Creación de servicio REST
#### 3.1 Creación del servicio REST
Consideraciones iniciales

Crear un servicio REST con Spring Boot no es solo escribir un controlador. Implica definir claramente qué capas tendrá la aplicación y qué responsabilidad asume cada una.

Spring propone una arquitectura por capas, muy alineada con el patrón MVC y con buenas prácticas de ingeniería del software.

#### A. Configuración del proyecto
¿Qué implica crear un proyecto Spring Boot?

Cuando creamos un proyecto Spring Boot:

- Definimos qué tipo de aplicación será (web, REST, batch, etc.).
- Indicamos qué dependencias necesitamos.
- Spring genera una estructura base y se encarga de:
    - Descargar librerías.
    - Configurar beans automáticamente.
    - Preparar el servidor embebido (Tomcat).

Dependencias habituales en un servicio REST

En el contexto del tema, lo habitual es incluir:
- Spring Web
Permite crear controladores REST y manejar peticiones HTTP.
- Spring Data JPA
Facilita el acceso a datos mediante repositorios.
- Driver del SGBD
MySQL, PostgreSQL, H2, etc.
- Lombok (opcional)
Reduce código repetitivo.
- DevTools (opcional)
Facilita el desarrollo.

Archivo application.properties
Aquí se configura:
- Puerto del servidor (server.port)
- Datos de conexión a la base de datos
- Opciones de Hibernate (DDL, logs SQL, etc.)


**El programa principal (@SpringBootApplication)**

Toda aplicación Spring Boot arranca desde una clase principal:
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

¿Qué hace realmente @SpringBootApplication?

No es una sola cosa, sino tres anotaciones combinadas:
1. @Configuration
    - Permite definir beans.
    - Marca la clase como fuente de configuración.

2. @EnableAutoConfiguration
    - Spring analiza las dependencias.
    - Configura automáticamente componentes necesarios.
    - Ejemplo: si detecta JPA y un driver, configura el EntityManager.

3. @ComponentScan
    - Busca clases anotadas (@Controller, @Service, @Repository, etc.)
    - Solo dentro del paquete base y subpaquetes.

#### B. Modelo (entidades)
Qué es el modelo en un servicio REST

El modelo representa los datos del dominio, y suele coincidir con:
- Las tablas de la base de datos.
- Las entidades JPA.

Ejemplo conceptual:
- Director
- Película
- Usuario
- Departamento

Características del modelo
- Son POJOs.
- Contienen atributos, constructores, getters y setters.
- Están anotadas con JPA (@Entity, @Id, etc.).
- No contienen lógica de presentación ni de acceso HTTP.

Relación con temas anteriores
Aquí se reutiliza directamente:
- Hibernate
- JPA
- Relaciones (@OneToMany, @ManyToMany, etc.)

El modelo no sabe que existe un servicio REST. Solo sabe persistirse.

#### C. Repositorio (Repository)
Qué es un repositorio

El repositorio es la capa de acceso a datos.

En Spring Data:
- Se define como una interfaz.
- Spring genera la implementación automáticamente.

Ejemplo conceptual:
```java
public interface DirectorRepository extends JpaRepository<Director, Long> {
}
```
Qué nos aporta Spring Data
Sin escribir SQL ni implementación:
- CRUD completo.
- Métodos derivados por nombre.
- Soporte para paginación y ordenación.

Relación con JDBC
Internamente:
- Spring Data usa Hibernate.
- Hibernate usa JDBC.
- JDBC habla con el SGBD.

Spring abstrae el acceso a datos, igual que Hibernate abstraía SQL.

#### D. Servicio (Service)
Por qué existe la capa Service

Aunque podríamos llamar al repositorio directamente desde el controlador, no es buena práctica.

El servicio:
- Centraliza la lógica de negocio.
- Permite reutilizar código.
- Facilita pruebas unitarias.
- Evita controladores “gordos”.

Patrón habitual
- Interfaz del servicio. 
- Implementación concreta.

Uso de @Service
- Marca la clase como componente gestionado por Spring.
- Permite inyección automática.

Idea clave
El servicio no sabe nada de HTTP, solo de lógica.

#### E. Controlador (Controller)
Rol del controlador
El controlador es el punto de entrada de las peticiones HTTP.

Se encarga de:
- Mapear URLs.
- Recoger parámetros.
- Llamar al servicio adecuado.
- Devolver la respuesta.

@RestController
- Indica que la clase es un controlador REST.
- Devuelve datos directamente (JSON).
- Evita vistas HTML.

Anotaciones más importantes
- @GetMapping
- @PostMapping
- @PutMapping
- @DeleteMapping
- @PathVariable
- @RequestBody
- @RequestParam

Flujo de una petición

1. El cliente hace una petición HTTP.
2. Spring busca el controlador adecuado.
3. Ejecuta el método mapeado.
4. El método devuelve un objeto.
5. Spring lo serializa a JSON.
6. Se envía la respuesta.

**Uso de Optional**

Spring Data devuelve Optional<T> para:
- Evitar valores nulos.
- Obligar a tratar el caso “no existe”.

Esto conecta con:
- Programación defensiva.
- Mejores prácticas Java modernas.

**Uso de ResponseEntity**

Permite:
- Devolver datos.
- Controlar el código HTTP.
- Gestionar errores de forma explícita.

Ejemplos de estados:
- 200 OK
- 201 CREATED
- 404 NOT FOUND
- 400 BAD REQUEST

#### 3.2 Pruebas del servicio REST con Postman
Por qué usar Postman
- El navegador solo hace GET.
- REST usa múltiples métodos HTTP.
- Postman permite enviar JSON en el cuerpo.

Qué se prueba
- Que los endpoints funcionan.
- Que los códigos HTTP son correctos.
- Que el JSON devuelto es el esperado.

### Resumen global de la sección

Un servicio REST en Spring Boot se basa en:
- Separación clara de responsabilidades
- Inyección de dependencias
- Convenciones antes que configuración
- Reutilización de lo aprendido en Hibernate/JPA

### Sección 4. Generación de contenidos dinámicos con Spring.
#### 4.1 Creación de una aplicación web con Spring y MVC
De API REST a aplicación web

En la sección anterior hemos construido un servicio REST, donde:
- El cliente (Postman, navegador, app móvil…) recibe JSON
- El controlador está anotado con @RestController
- Los métodos devuelven objetos Java → JSON automáticamente

Ahora damos un paso más:
Queremos mostrar esos datos en una página web, no en JSON.

Eso implica:
- Volver al patrón MVC
- Añadir una capa de vista
- Transformar los datos en HTML

Recordatorio del patrón MVC
| Capa            | Responsabilidad                                              |
| --------------- | ------------------------------------------------------------ |
| **Modelo**      | Contiene los datos (entidades, listas, objetos)              |
| **Vista**       | Presenta los datos (HTML)                                    |
| **Controlador** | Recibe la petición, obtiene datos y decide qué vista mostrar |

En Spring MVC:

- El controlador recibe la petición HTTP
- Recupera datos del servicio
- Los mete en un objeto Model
- Devuelve el nombre de una vista
- Thymeleaf genera el HTML final

#### 4.2 Estructura del proyecto

Cuando usamos Spring Boot con MVC, el proyecto tiene esta estructura típica:
```css
src/
 └── main/
     ├── java/
     │   └── com.ejemplo.app
     │       ├── controller
     │       ├── model
     │       ├── repository
     │       └── service
     └── resources/
         ├── static/
         │   ├── css
         │   ├── js
         │   └── img
         ├── templates/
         │   └── *.html
         └── application.properties
```
Explicación por carpetas.
**Controller**
- Clases que gestionan las peticiones HTTP
- Deciden qué vista se devuelve
- Conectan con los servicios
**Model**
- Entidades (JPA)
- Clases de dominio
**Service**
- Lógica de negocio
- Orquesta repositorios y reglas
**Repository**
- Acceso a base de datos
- Interfaces JpaRepository
**Resources/Static**
Aquí van los recursos estáticos:
- CSS
- JavaScript
- Imágenes

Nunca pasan por el controlador, Spring los sirve directamente.
**Resources/Templates**
Aquí van las vistas HTML dinámicas:
- Archivos .html
- Usan sintaxis Thymeleaf
- Se procesan en el servidor

El controlador devuelve el nombre del archivo, no la ruta.

#### 4.3 El motor de plantillas: Thymeleaf
¿Qué es Thymeleaf?
- Es un motor de plantillas HTML que permite:
- Mezclar HTML + datos Java
- Generar páginas dinámicas
- Mantener HTML válido (no rompe el diseño)

Spring Boot lo integra automáticamente.

Cómo funciona el flujo
1. El controlador devuelve "productos"
2. Spring busca productos.html en /templates
3. Thymeleaf procesa las expresiones
4. Se genera HTML final
5. El navegador lo muestra

#### 4.4 Cambios en el controlador
Antes: controlador REST
```java
@RestController
public class ProductoController {

    @GetMapping("/productos")
    public List<Producto> getProductos() {
        return productoService.findAll();
    }
}
```
- Devuelve JSON
- No hay vistas

Ahora: Controlador MVC
```java
@Controller
public class ProductoController {

    @GetMapping("/productos")
    public String getProductos(Model model) {
        List<Producto> productos = productoService.findAll();
        model.addAttribute("productos", productos);
        return "productos";
    }
}
```
Devuelve una vista
- Usa Model para pasar datos
- Thymeleaf se encarga del HTML

¿Qué es Model?

Es un contenedor de datos que:
- Se pasa a la vista
- Funciona como un Map<String, Object>
- Permite acceder a los datos desde HTML
```java
model.addAttribute("productos", lista);
```
En la vista:
```html
${productos}
```

#### 4.5 La vista con Thymeleaf
Plantilla básica
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Productos</title>
</head>
<body>

<h1>Listado de productos</h1>

<table>
    <tr>
        <th>Descripción</th>
        <th>Precio</th>
    </tr>
    <tr th:each="p : ${productos}">
        <td th:text="${p.descripcion}"></td>
        <td th:text="${p.precio}"></td>
    </tr>
</table>

</body>
</html>
```

Conceptos clave de Thymeleaf
**th:each**
- Itera sobre colecciones
- Equivalente a un for
```html
<tr th:each="p : ${productos}">
```

**th:text**
- Sustituye el contenido del elemento
- Evita inyección HTML
```html
<td th:text="${p.precio}"></td>
```
Acceso a propiedades
- Usa getters automáticamente
- ${p.descripcion} → getDescripcion()

#### 4.6 Renderizado condicional
```html
<td th:if="${admin}">ADMIN</td>
```
- Solo se muestra si la condición es verdadera
- No se envía al navegador si no se cumple

#### 4.7 Formularios con Thymeleaf
Formulario típico
```html
<form th:action="@{/productos/save}" th:object="${producto}" method="post">

    <input type="hidden" th:field="*{id}" />

    <input type="text" th:field="*{descripcion}" />
    <input type="number" th:field="*{precio}" />

    <button type="submit">Guardar</button>

</form>
```

Explicación paso a paso

**th:object**
Asocia el formulario a un objeto Java
```html
th:object="${producto}"
```

**th:field**
- Enlaza campos HTML con atributos del objeto
```html
th:field="*{precio}"
```

- Spring rellena el objeto automáticamente
- Evita escribir @RequestParam

Controlador que recibe el formulario
```java
@PostMapping("/productos/save")
public String guardarProducto(@ModelAttribute Producto producto) {
    productoService.save(producto);
    return "redirect:/productos";
}
```
- @ModelAttribute crea el objeto a partir del formulario
- redirect: evita reenvíos duplicados

#### 4.8 Navegación entre páginas
```html
<a th:href="@{/productos}">Volver</a>
```
- URLs dinámicas
- Evita rutas hard-codeadas

#### 4.9 Conclusión de la sección

Con esta sección ya sabemos:

- Crear aplicaciones web con Spring Boot
- Aplicar MVC real
- Separar lógica, datos y presentación
- Usar Thymeleaf para HTML dinámico
- Crear formularios y flujos completos
- Conectar base de datos → usuario final

### Resumen 
| Tema             | Qué aporta          |
| ---------------- | ------------------- |
| JDBC / Hibernate | Persistencia        |
| Spring Boot      | Infraestructura     |
| REST             | Servicios           |
| MVC              | Presentación        |
| Thymeleaf        | HTML dinámico       |
| Formularios      | Interacción usuario |
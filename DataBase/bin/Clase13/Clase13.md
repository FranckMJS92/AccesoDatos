## TEMA 5
### Sección 1. Introducción. Componentes de software.
### 1.1 Componentes de software
#### 1.1.1 ¿Por qué se habla de “componentes” al empezar un proyecto?

Idea muy importante: antes de programar, se analiza el problema. Cuando analizamos, no solo pensamos “qué hay que hacer”, sino:

- ¿cómo lo organizo para que no sea un caos dentro de 2 meses?
- ¿cómo lo hago para que otro compañero pueda entenderlo?
- ¿cómo evito duplicar código?
- ¿cómo hago que sea ampliable?

Aquí entra la programación por componentes: se intenta construir la aplicación como piezas.

#### 1.1.2 ¿Qué es exactamente un componente?

Un componente es una parte de la solución que está “cerrada” por dentro y ofrece hacia fuera:
- comportamiento (lo que hace),
- interfaz (cómo se usa).

Es decir, como:
- un componente es una “parte de la solución”,
- y su valor real es que puedes reutilizarlo en otros proyectos.

Ejemplo:

Imagina que haces un componente llamado:

- RepositorioJuegos (o DAOJuegos)

Por fuera, el resto del programa solo sabe esto:

- listarJuegos()
- insertarJuego(juego)
- borrarJuego(id)
- buscarPorGenero("Aventura")

Por dentro, podría hacer la consulta usando:
- JDBC con H2,
- JDBC con MySQL,
- Hibernate,
- o incluso ficheros (si fuese un ejemplo de tema 1).

La clave del componente es que nadie debería depender del interior.

#### 1.1.3 Características de un componente.

Se lista 3 características

- A) Encapsulado e independiente

Encapsulado: el interior del componente está “protegido”. No accedes a sus datos internos como te dé la gana.

Independiente: el componente no necesita conocer cómo están hechos los demás, solo se comunica por la interfaz.

Ejemplo:
La parte de “acceso a datos” no debería saber cómo se pinta un botón en el frontend.
Y el frontend no debería saber cómo se construye una consulta SQL.

Cada uno en su bloque.

B) Independencia de software, hardware y plataforma

Este punto es muy “realista”:

idealmente, el componente debería funcionar en varios entornos, o al menos minimizar dependencias.

Ejemplo:
Si tu componente funciona solo en un Windows concreto por rutas fijas tipo C:\..., entonces no es portable. Por eso insistimos en rutas relativas, configuración externa, etc.

C) Reutilizable

Esto es lo que da sentido a todo:

haces el componente una vez, y lo reutilizas en varios proyectos.

Ejemplo:
Si en 1º hicimos una clase ConexionBD para JDBC, en 2º la reutilizas. Y si mañana cambias H2 por MySQL, idealmente solo tocarías esa parte.

#### 1.1.4 Contexto histórico

Microsoft impulsó componentes en escritorio con tecnologías como OLE/COM, que luego evolucionan hacia componentes más modernos. En Java, se mencionan:

- JavaBeans (Java SE): clases “de datos” con ciertas reglas (getters/setters, constructor vacío…).

- EJB (Java EE / Jakarta EE): componentes empresariales (más complejos, servidor de aplicaciones, etc.).

- Conexión con el módulo:
Aunque no vayamos a “dar EJB”, sí estamos usando el espíritu: separar en componentes, y más adelante Spring hace esto de forma moderna.

### 1.2 Aplicaciones web

Hoy no desarrollamos solo escritorio. Hay: móviles, tablets, IoT, navegadores, servicios cloud…

Eso obliga a pensar en arquitectura cliente-servidor.

#### 1.2.1 Idea clave: ¿Por qué web?

Porque la aplicación no vive solo en un PC. El usuario interactúa desde un dispositivo (cliente). Los datos suelen vivir en un servidor (backend + BD).

#### 1.2.2 Frontend vs Backend

**Frontend**. Es lo que ve el usuario:

interfaz (botones, formularios, pantallas), experiencia de usuario (UX), validaciones visuales navegación.

- En web:

HTML/CSS/JS, frameworks…

- En escritorio:

JavaFX, .NET, etc.

- En móvil:

Android / iOS.

**Importante para Acceso a Datos:**
El frontend normalmente NO habla directamente con la base de datos.
El frontend llama a un backend (API), y el backend consulta la BD.

**Backend**

Es la parte que está en el servidor:

recibe peticiones (por ejemplo HTTP), ejecuta lógica (cálculos, reglas de negocio), accede a datos (BD), devuelve respuesta.

JDBC, Hibernate, Spring Data, consultas, transacciones

#### 1.2.3 Fullstack

Fullstack es conocer ambas partes.

- no necesitas ser fullstack para entender el flujo, pero sí necesitas entender el “viaje” de los datos.

### 1.3 Modelo Vista Controlador (MVC)

MVC es una arquitectura para organizar aplicaciones.

#### 1.3.1 Roles en MVC
Vista
- lo que se muestra al usuario
- recibe datos ya preparados para mostrar

Controlador
- recibe la petición del usuario
- decide qué acción ejecutar
- coordina el proceso (no debe tener “lógica de BD” pesada)

Modelo
- gestiona los datos y la lógica
- aquí suele estar:
    - acceso a datos (DAO/Repository)
    - lógica de negocio
    - validaciones “serias” (no solo de interfaz)

#### 1.3.2 Flujo 1–7
1) El usuario lanza una petición

Ejemplo: el usuario pulsa “Ver juegos”.

2) Llega al controlador

En web sería una ruta:

/juegos

/api/juegos

3) El controlador habla con el modelo

El controlador llama a un servicio o repositorio:

juegoService.listar()

4) El modelo consulta la BD

Aquí se ejecuta:

JDBC / Hibernate / etc.

5) La BD devuelve resultados

Los resultados vuelven al modelo:

ResultSet → objetos

entidades Hibernate

6) El modelo entrega datos al controlador

El controlador recibe los datos ya listos.

7) El controlador responde al cliente

En MVC clásico: devuelve una vista (HTML).
En REST: devuelve JSON.

#### 1.3.3 Modelo REST

REST como alternativa común hoy.

REST = Representational State Transfer

El servidor expone recursos:

- /api/juegos

- /api/juegos/5

Y el cliente consume esos recursos.

Operaciones CRUD en REST
- GET: consultar
- POST: crear
- PUT: modificar
- DELETE: borrar

en JDBC: INSERT, SELECT, UPDATE, DELETE

en REST: POST/GET/PUT/DELETE

#### 1.3.4 HATEOAS

La idea:

- la respuesta no solo trae datos,
- también trae enlaces a acciones relacionadas.

Ejemplo conceptual:

- recibes un juego
- y enlaces para “ver sus reviews”, “editar”, “borrar”, etc.

“API más navegable”.

### 1.4 Spring Boot

Vamos a centrarnos en Spring y Spring Boot como entorno de trabajo.

#### 1.4.1 ¿Por qué Spring?

Porque en backend Java, Spring se volvió el estándar para:

- crear aplicaciones de servidor,
- organizar componentes,
- conectar capas (controller/service/repository),
- manejar transacciones,
- etc.

#### 1.4.2 Conceptos clave
A) Inyección de dependencias (IoC)

En vez de:
```java
Servicio s = new Servicio(new Repositorio());
```

Spring lo hace por ti:

- detecta clases marcadas como componentes,
- crea instancias,
- las inyecta donde corresponda.

Ventaja:
te obliga a pensar en “piezas”, no en “todo en un main”.

B) Gestión de componentes POJO y Beans

- POJO: clase normal Java.
- Bean en Spring: un objeto gestionado por el contenedor Spring.

Esto encaja con “componentes”: Spring administra esos componentes.

C) Programación orientada a aspectos (AOP)

Sirve para separar preocupaciones transversales:
- logs,
- seguridad,
- transacciones,
- métricas…

sin meter ese código repetido en todos los métodos.

D) Proxies

Spring puede envolver un objeto y añadir comportamiento.

Ejemplo típico:
- abro transacción antes de ejecutar método
- cierro/commit al final
- rollback si hay error

Tú no lo escribes a mano cada vez.

#### 1.4.3 ¿Qué aporta Spring Boot específicamente?

Spring Boot “resume” las fases y arranca rápido. Suele traer un servidor embebido (Tomcat) y permite desplegar sin complicaciones.


### RESUMEN
1. EN proyectos reales no se programa "a lo loco". Se organiza en componentes.
2. Un componente se caracteriza por ser encapsulado, independiente y reutilizable.
3. En aplicaciones modernas distiguimos entre **FRONTEND** (interfaz) y **BACKEND** (lógica + datos).
4. MVC organiza el sistema en Vista/Controlador/Modlo y el flujo de petición.
5. REST es la forma mas comun hoy en día, el backend devuelve datos (JSON) y el fronted muestra dichos datos.
6. Spring/Spring Boot facilitan proyectos backends en Java, gestionando componentes e inyecciones.
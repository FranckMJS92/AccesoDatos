# Actividad: Personal de un centro educativo con PostgreSQL (BDOR)

Vamos a modelar un caso típico de herencia en un centro educativo usando características objeto-relaciones de PostgreSQL.
 
## Datos comunes de cualquier Empleado-Centro

De cada empleado se almacenerá:

- nombreCompleto
- ubicación (tipo de vía: calle/avenida /plaza, nombre d vía y número)
- teléfonos (lista de teléfonos, 0...N)
- supervisorDirecto, que será otro EmpleadoCentro (auto-referencia)
 
## Tipos de empleado

Los empleados pueden ser:
1) Docente
    - especialidad: progrmación / bases_datos / sistemas
    - horasSemanales: entero (por ejemplo 1-40)
 
2) Administrativo
    - plusProductividad: número (doble /numeric)
    - area: secretaría /  administración / dirección
 
# Requisitos técnicos (usar todos los conceptos aprendidos del tema)

Debes utilizar obligatoriamente:
- ENUM
- tipo estructurado (composite type)
- arrays (colecciones)
- herencia con INHERITS
- auto-referencia (supervisor)
- consultas (3 distintas):
    - Una consulta que muestre solo los docentes y su supervisor (con JOIN).
    - Una consulta que muestre los Administrativos filtrando por área y ordenando por plus.
    - Una consulta que muestre cada teléfono en una fila.
 
--- ENTREGABLES ---
1. creación de tipos y tablas
2. Inserción de datos de prueba (mínimo: 2 docentes, 2 administrativos, 1 supervisor)
3. Las tres consultas pedidas.

---- SOLUCIÓN ----

1) Crear tipos ENUM

```sql
-- Tipo de vía
CREATE TYPE tipo_via AS ENUM ('calle', 'avenida', 'plaza');

-- Especialidad del docente
CREATE TYPE especialidad_docente AS ENUM ('Programación','Base de DAtos','Sistemas');

-- Área de personal administrativo
CREATE TYPE area_administrativo AS ENUM ('Secretaría','Administración','Dirección');
```

2) Crear tipo estructurado de UBICACION

```sql
CREATE TYPE Ubicacion AS (
    tipo tipo_via,
    nombre_via varchar,
    numero int
);
```
3) Crear la tabla base Empleado

```sql
CREATE TABLE Empleado(
    idEmpleado serial PRIMARY KEY,
    nombre varchar NOT NULL,

    -- Tipo compuesto
    ubicacion Ubicacion,

    -- Colección de emails
    telefonos varchar[],

    -- Auto-referencia: responsable es otro trabajador
    responsable int, 
    CONSTRAINT fk_responsable
        FOREING KEY (responsable)
        REFERENCES Empleado(idEmpleado)
);
```

1) Crear tablas hijas con herencia
- Técnico
```sql
CREATE TABLE Especialidad (
    especialidad especialidad_docente,
    horas_semanales int CHECK (nivel BETWEEN 1 AND 40)
) INHERITS (Trabajador);
```
- Gestor
```sql
CREATE TABLE Area(
    complemento numeric(8,2),
    area area_administrativo
) INHERITS (Trabajador);
```
-- HASTA AQUI

1) Insertar datos de ejemplo
- Insertat un trabajador base (será responsable)
```sql
INSERT INTO Trabajador(nombre, localizacion, emails)
VALUES (
    'Luis Sánchez',
    ('calle','Picasso',10),
    ARRAY['luis@emails.com']
);
```
- Insertar un técnico con responsable LUIS => id=1
```sql
INSERT INTO Tecnico(nombre, localizacion, emails, especialidad nivel, responsable)
VALUES (
    'Ana García',
    ('avenida','Mediterraneo', 12),
    ARRAY['ana@emails.com', 'ana.soporte@emails.com],
    'soporte',
    2,
    1
);
```

- Insertar un Gestor
```sql
INSERT INTO Gestor(nombre, localizacion, emails, complemento, departamento, responsable)
VALUES (
    'José Matarín',
    ('plaza', 'Almería', 5),
    ARRAY[jose@emials.com],
    950.50,
    'IT',
    1
);
```

6) Consultas clave
- Ver todos (incluye subtablas)
```sql
SELECT * FROM Trabajador;
```
- Ver solo trabajadores "base"
```sql
SELECT * FROM ONLY Trabajador;
```
- Mostar emails como filas (colección -> filas)
```sql 
SELECT nombre, unnest(emails) AS emails
FROM Trabajador;
```

# RESUMEN ACTIVIDAD
- ENUM restringe valores (dominios cerrados)
- Composite type agrupa datos (Localizacion)
- Arrays permiten colecciones en una columna
- INHERITS permite herencia entre tablas
- ONLY ingonara herencia
- Auto-FK modela relaciones "jefe->empleado"
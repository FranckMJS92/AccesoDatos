# Actividad: Personal de empresa con herencia y tipos (PostgreSQL BDOR)

Vamos a modelar un caso típico de herencia en una empresa usando características objeto-relacionales de PostgreSQL.

## Datos comunes de cualquier Trabajador

De cada trabajador se almacenará:

- nombre
- localización (tipo de vía: calle/avenida/plaza, nombre de vía y número)
- lista de emails (mínimo 0, máximo el que sea)
- su responsable directo, que será otro trabajador (auto-referencia)

## Tiempos de trabajador

Los trabajadores pueden ser:

- Técnico
    - especialidad: software / redes / soporte
    - nivel: entero (1–3)

- Gestor
    - complemento salarial
    - departamento: Ventas / IT / RRHH / Finanzas

## Requisitos

Debes usar todos los conceptos del tema:

* ENUM
* tipo estructurado (composite type)
* arrays (colecciones)
* herencia con INHERITS
* auto-referencia (responsable)
* consultas que incluyan:

    * SELECT * FROM Trabajador
    * SELECT * FROM ONLY Trabajador
    * unnest() para mostrar emails

----- SOLUCIÓN -----

1) Crear ls tipos ENUM

```sql 
-- Tipo de vía (calle, avenida, plaza)
CREATE TYPE tipo_via AS ENUM ('calle', 'avenida', 'plaza');

-- Especialidad del técnico (software, redes, soporte)
CREATE TYPE especialidad_tecnico AS ENUM ('software', 'redes', 'soporte');

-- Departamentos para gestor (Ventas, IT, RRHH, Finanzas)
CREATE TYpe departamento_gestor AS ENUM ('Ventas', 'IT', 'RRHH', 'Finanzas');
```

2) Crear el tipo estructurado de  LOCALIZACIÓN
```sql
CREATE TYPE Localizacion AS (
    tipo tipo_via,
    nombre_via varchar,
    numero int
);
```

3) Crear la tabla base Trabajador
```sql
CREATE TABLE Trabajador(
    idTrabajador serial PRIMARY KEY,
    nombre varchar NOT NULL,

    -- Tipo compuesto
    localizacion Localizacion,

    -- Colección de emails
    emails varchar[],

    -- Auto-referencia: responsable es otro trabajador
    responsable int, 
    CONSTRAINT fk_responsable
        FOREING KEY (responsable)
        REFERENCES Trabajador(idTrabajador)
);
```

4) Crear tablas hijas con herencia
- Técnico
```sql
CREATE TABLE Tecnico (
    especialidad especialidad_tecnico,
    nivel int CHECK (nivel BETWEEN 1 AND 3)
) INHERITS (Trabajador);
```
- Gestor
```sql
CREATE TABLE Gestor(
    complemento numeric(8,2),
    departamento departamento_gestor
) INHERITS (Trabajador);
```

5) Insertar datos de ejemplo
- Insertar un trabajador base (será responsable)
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
INSERT INTO Tecnico(nombre, localizacion, emails, especialidad, nivel, responsable)
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
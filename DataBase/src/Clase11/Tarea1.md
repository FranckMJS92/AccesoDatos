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
    - especialidad: programación / bases_datos / sistemas
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
1. Creación de tipos y tablas
2. Inserción de datos de prueba (mínimo: 2 docentes, 2 administrativos, 1 supervisor)
3. Las tres consultas pedidas.

---- SOLUCIÓN ----

##  Creación de tipos y tablas

### Crear tipos ENUM

```sql
-- Tipo de vía
CREATE TYPE tipo_via AS ENUM ('calle', 'avenida', 'plaza');

-- Especialidad del docente
CREATE TYPE especialidad_docente AS ENUM ('Programación','Base de Datos','Sistemas');

-- Área de personal administrativo
CREATE TYPE area_administrativo AS ENUM ('Secretaría','Administración','Dirección');
```

### Crear tipo estructurado de Ubicación

```sql
CREATE TYPE Ubicacion AS (
    tipo tipo_via,
    nombre_via varchar,
    numero int
);
```
### Crear la tabla base Empleado

```sql
CREATE TABLE Empleado(
    idEmpleado serial PRIMARY KEY,
    nombre varchar NOT NULL,

    -- Tipo compuesto
    ubicacion Ubicacion,

    -- Colección de teléfonos
    telefonos varchar[],

    -- Auto-referencia: supervisor es otro empleado
    supervisor int, 
    CONSTRAINT fk_supervisor
        FOREIGN KEY (supervisor)
        REFERENCES Empleado(idEmpleado)
);
```

### Crear tablas hijas con herencia
- Técnico
```sql
CREATE TABLE Docente (
    especialidad especialidad_docente,
    horas_semanales int CHECK (horas_semanales BETWEEN 1 AND 40)
) INHERITS (Empleado);
```
- Gestor
```sql
CREATE TABLE Administrativo(
    area area_administrativo
    complemento numeric(6,2),
) INHERITS (Empleado);
```

## Inserción de datos de prueba

- Insertar un empleado base (será supervisor)
```sql
INSERT INTO Empleado(nombre, ubicacion, telefonos)
VALUES (
    'Splinter',
    ('calle','Valmojado',47),
    ARRAY['991706343','661603321']
);
```
- Inserción de docentes con responsable Francisco => id=1

```sql
INSERT INTO Docente(nombre, ubicacion, telefonos, especialidad, horas_Semanales, supervisor)
VALUES (
    'Leonardo',
    ('plaza','Virgen del Puerto', 67),
    ARRAY['648316425', '689546132'],
    'Programación',
    20,
    1
);
```

```sql
INSERT INTO Docente(nombre, ubicacion, telefonos, especialidad, horas_Semanales, supervisor)
VALUES (
    'Donatelo',
    ('plaza','Virgen del Puerto', 67),
    ARRAY['648316425', '689546132'],
    'Bsee de Datos',
    20,
    1
);
```

- Insertar de administrativos con responsable Francisco => id=1
```sql
INSERT INTO Administrativo(nombre, ubicacion, telefonos, area, complemento, supervisor)
VALUES (
    'Rafael',
    ('calle', 'Escalona', 82),
    ARRAY['644573315','682458796'],
    'Secretaría',
    150.00,
    1
);
```

```sql
INSERT INTO Administrativo(nombre, ubicacion, telefonos, area, complemento, supervisor)
VALUES (
    'Miguel Angel',
    ('calle', 'Illescas', 96),
    ARRAY['661514231'],
    'Administración',
    100.00,
    1
);
```
## Consultas

- Una consulta que muestre solo los docentes y su supervisor (con JOIN).

```sql
SELECT 
    d.nombre AS docente,
    d.especialidad,
    d.horas_semanales,
    e.nombre AS supervisor
FROM Docente d
JOIN Empleado e ON d.supervisor = e.idEmpleado;
```

 - Una consulta que muestre los Administrativos filtrando por área y ordenando por plus.

```sql
SELECT 
    nombre,
    area,
    complemento,
    (ubicacion).tipo || ' ' || (ubicacion).nombre_via || ', ' || (ubicacion).numero AS direccion
FROM Administrativo
WHERE area = 'Secretaría' 
ORDER BY complemento DESC;
```

 - Una consulta que muestre cada teléfono en una fila.

```sql 
SELECT 
    idEmpleado,
    nombre,
    UNNEST(telefonos) AS telefono
FROM Empleado
ORDER BY idEmpleado;
```

# RESUMEN ACTIVIDAD
- ENUM restringe valores (dominios cerrados)
- Composite type agrupa datos (Localizacion)
- Arrays permiten colecciones en una columna
- INHERITS permite herencia entre tablas
- ONLY ingonara herencia
- Auto-FK modela relaciones "jefe->empleado"
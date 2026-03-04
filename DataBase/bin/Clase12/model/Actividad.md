# Ejercicio: Crear y gestionar ObjectDB

Para finalizar el tema, vamos a desarrollar un caso completo, **desde el diseño de la base de datos hasta su implementación y uso**.  
Se trata de la gestión de una **pequeña empresa**, con los siguientes requisitos:

## Requisitos de datos

### Empleados
- De cada empleado se necesita:
  - **Nombre**
  - **Fecha de contratación**
  - **Tiempo trabajado en la empresa** (**no se almacena** en la BD; se calcula **en ejecución**).
- Cada empleado tiene asignada una **Dirección**:
  - **Calle**
  - **Número**

### Departamentos
- Los departamentos tienen:
  - **Nombre**
  - **Sede**
  - Un **identificador** (asignado por el sistema)
- Un departamento tiene asignados **varios empleados**.
- Cada empleado **solo puede pertenecer a un departamento**.

### Proyectos
- De los proyectos guardaremos:
  - **ID**
  - **Descripción**
- Debemos conocer:
  - Quién es el **jefe de proyecto** (un empleado)
  - Qué empleados **participan** en el proyecto
- Un empleado puede participar en **varios proyectos a la vez**, pero **solo puede ser jefe de uno**.

## Tareas del programa
Crea las clases necesarias y un programa que:
1. Inserte datos de ejemplo (departamentos, empleados y proyectos).
2. Resuelva estas consultas:
   1) **Proyectos sin empleados** (sin participantes).  
   2) **Empleados sin departamentos**.  
   3) **Empleados de un año concreto** (por fecha de contratación).  
   4) **Empleado de mayor antigüedad** (más antiguo).  
   5) **Empleados junto a la cantidad de proyectos** en los que están trabajando.

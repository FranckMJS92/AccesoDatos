package Clase12;

import java.time.LocalDate;

import javax.swing.text.html.parser.Entity;

import Clase12.model.Departamento;
import Clase12.model.Direccion;
import Clase12.model.Empleado;
import Clase12.model.Proyecto;

public class MainEmpresaObjectDB {

        public static void main(String[] args) {

                // PASO 1. leer persistence.xml y crea el acceso a la BD
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("empresa.odb");

                // PASO 2. Crear el EntityManager
                EntityManager em = emf.createEntityManager();

                try {

                        // PASO 3. INSERCCION DE DATOS
                        // PASO 3.1 Iniciar
                        em.getTransaction().begin();

                        // PASO 3.2 Crear departamentos
                        Departamento dIt = new Departamento("IT", "Cáceres");
                        Departamento dVentas = new Departamento("Ventas", "Barcelona");

                        em.persist(dIt);
                        em.persist(dVentas);

                        // PASO 3.3 Crear empleado
                        Empleado julio = new Empleado(
                                        "Julio",
                                        LocalDate.of(2026, 1, 28),
                                        new Direccion("C/Mayor", 5));

                        Empleado sergio = new Empleado(
                                        "Sergio",
                                        LocalDate.of(2025, 1, 28),
                                        new Direccion("C/Menor", 15));

                        em.persist(julio);
                        em.persist(sergio);

                        // PASO 3.4 Asignar empleados a departamentos
                        dIt.addEmpleado(sergio);
                        dVentas.addEmpleado(julio);

                        // PASO 3.5 Crear Proyectos
                        Proyecto p1 = new Proyecto("Acceso a datos", sergio); // sergio es el jefe
                        Proyecto p2 = new Proyecto("Acceso a ventas", julio); // julio es el jefe

                        em.persist(p1);
                        em.persist(p2);

                        // PASO 3.6 Añadir gente al proyecto
                        p1.addParticipante(sergio);
                        p2.addParticipante(julio);

                        // PASO 4. CONSULTAS
                        System.out.println("Proyectos sin empleados");
                        List<Proyecto> proyectosSinParticipantes = em.createQuery(
                                        "SELECT p FROM Proyecto p WHERE p.paricipantes IS EMPTY", Proyecto.class)
                                        .getResultList();

                        System.out.println("Empleados sin departamentos");
                        List<Proyecto> empleadosSinDepartamentos = em.createQuery(
                                        "SELECT e FROM Empleado e WHERE e.departamento IS NULL", Proyecto.class)
                                        .getResultList();

                        int year = 2025;
                        LocalDate ini = LocalDate.of(year, 1, 1);
                        LocalDate fin = LocalDate.of(year, 12, 31);
                        List<Empleado> empleadoYear = em.createQuery(
                                        "SELECT e FROM Empleado eWHERE e.fechaContratacion BETWEEN :ini AND :fin",
                                        Empleado.class)
                                        .setParameter("ini", ini)
                                        .setParameter("fin", fin)
                                        .getResultList();
                } finally {
                        // PASO 5. Cerrar recursos
                        em.close();
                        emf.close();
                }
        }
}
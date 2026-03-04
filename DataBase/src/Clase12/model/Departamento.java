package Clase12.model;

@Entity
public class Departamento {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String sede;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL)
    private List<Empleado> empleados = new ArrayList<>();

    public Departamento() {
    }

    public Departamento(String nombre, String sede) {
        this.nombre = nombre;
        this.sede = sede;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSede() {
        return sede;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void addEmpleado(Empleado e) {
        empleados.add(e);
        e.setDepartamento(this);
    }

}

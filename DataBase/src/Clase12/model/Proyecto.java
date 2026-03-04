package Clase12.model;

@Entity
public class Proyecto {

    @Id
    @GeneratedValue
    private Long id;

    private String descripcion;

    @ManyToOne
    private Empleado jefe;

    @ManyToMany
    private Set<Empleado> participantes = new HashSet<>();

    public Proyecto() {
    }

    public Proyecto(String descripcion, Empleado jefe) {
        this.descripcion = descripcion;
        this.jefe = jefe;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Empleado getJefe() {
        return jefe;
    }

    public Set<Empleado> getParticipantes() {
        return participantes;
    }

    public void addParticipante(Empleado e) {
        participantes.add(e);
        e.getProyectos().add(this);
    }

}

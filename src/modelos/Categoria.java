package modelos;

/**
 * Modelo de la entidad Categoria
 * 
 * @author josha
 */
public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;

    // Constructor sin descripción
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    public Categoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

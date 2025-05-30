package modelos;
/**
 *
 * @author josha
 */
public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private String foto;

    
    public Categoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Categoria(int id, String nombre, String descripcion, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }
 
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Método equals() para comparar categorías por ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Categoria other = (Categoria) obj;
        return id == other.id;
    }

    /**
     * Método hashCode() consistente con equals()
     */
    @Override
    public int hashCode() {
        return id;
    }
}

package modelos;

public class Ropa {
    private int id;
    private String modelo;
    private String nombre;
    private Categoria categoria;
    private String talla;
    private String color;
    private double precio;
    private String imagen; // ← NUEVO CAMPO

    // Constructor actualizado con imagen
    public Ropa(int id,String modelo, String nombre, Categoria categoria, String talla, String color, double precio, String imagen) {
        this.id = id;
        this.modelo=modelo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.talla = talla;
        this.color = color;
        this.precio = precio;
        this.imagen = imagen;
    }
    
    // Constructor sin ID
public Ropa(String modelo,String nombre, Categoria categoria, String talla, String color, double precio, String imagen) {
    this.modelo=modelo;
    this.nombre = nombre;
    this.categoria = categoria;
    this.talla = talla;
    this.color = color;
    this.precio = precio;
    this.imagen = imagen;
}


    // Getters
    public int getId() { return id; }
    public String getModelo(){return modelo;}
    public String getNombre() { return nombre; }
    public Categoria getCategoria() { return categoria; }
    public String getTalla() { return talla; }
    public String getColor() { return color; }
    public double getPrecio() { return precio; }
    public String getImagen() { return imagen; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setTalla(String talla) { this.talla = talla; }
    public void setColor(String color) { this.color = color; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setImagen(String imagen) { this.imagen = imagen; }
}

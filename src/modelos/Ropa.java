package modelos;

import java.math.BigDecimal;

/**
 *
 * @author josha
 */


public class Ropa {
    private int id;
    private String modelo;
    private String nombre;
    private String marca;
    private String color;
    private String talla;
    private BigDecimal precio;
    private BigDecimal descuento;
    private boolean disponible;
    private String descripcion;
    private String imagen;
    private Categoria categoria;

    public Ropa(int id, String modelo, String nombre, String marca, String color, String talla,
                BigDecimal precio, BigDecimal descuento, boolean disponible, String descripcion,
                String imagen, Categoria categoria) {
        this.id = id;
        this.modelo = modelo;
        this.nombre = nombre;
        this.marca = marca;
        this.color = color;
        this.talla = talla;
        this.precio = precio;
        this.descuento = descuento;
        this.disponible = disponible;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public Ropa(String modelo, String nombre, String marca, String color, String talla,
                BigDecimal precio, BigDecimal descuento, boolean disponible, String descripcion,
                String imagen, Categoria categoria) {
        this.modelo = modelo;
        this.nombre = nombre;
        this.marca = marca;
        this.color = color;
        this.talla = talla;
        this.precio = precio;
        this.descuento = descuento;
        this.disponible = disponible;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public String getModelo() { return modelo; }
    public String getNombre() { return nombre; }
    public String getMarca() { return marca; }
    public String getColor() { return color; }
    public String getTalla() { return talla; }
    public BigDecimal getPrecio() { return precio; }
    public BigDecimal getDescuento() { return descuento; }
    public boolean isDisponible() { return disponible; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public Categoria getCategoria() { return categoria; }

    public void setId(int id) { this.id = id; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setColor(String color) { this.color = color; }
    public void setTalla(String talla) { this.talla = talla; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}

package modelos;

import java.math.BigDecimal;
import modelos.Categoria;
/**
 *
 * @author josha
 */


public class Ropa {
    private int id;
    private String modelo;
    private String nombre;
    private String color;
    private String talla;
    private BigDecimal precio;
    private boolean disponible;
    private String marca;
    private String descripcion;
    private String foto; 
    private BigDecimal descuento;
    private Categoria categoria;
    private int stock;

    public Ropa(int id, String modelo, String nombre, String marca, String color, String talla, BigDecimal precio, BigDecimal descuento, boolean disponible, String descripcion, String foto, Categoria categoria, int stock) {
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
        this.foto = foto;
        this.categoria = categoria;
        this.stock = stock;
    }

    public Ropa(String modelo, String nombre, String marca, String color, String talla, BigDecimal precio, BigDecimal descuento, boolean disponible, String descripcion, String foto, Categoria categoria, int stock) {
        this.modelo = modelo;
        this.nombre = nombre;
        this.marca = marca;
        this.color = color;
        this.talla = talla;
        this.precio = precio;
        this.descuento = descuento;
        this.disponible = disponible;
        this.descripcion = descripcion;
        this.foto = foto;
        this.categoria = categoria;
        this.stock = stock;
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
    public String getFoto() {return foto;}
    public Categoria getCategoria() { return categoria; }
    public int getStock() { return stock; }


    public void setId(int id) { this.id = id; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setColor(String color) { this.color = color; }
    public void setTalla(String talla) { this.talla = talla; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public void setFoto(String foto) {this.foto = foto;}
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setStock(int stock) { this.stock = stock; }

}

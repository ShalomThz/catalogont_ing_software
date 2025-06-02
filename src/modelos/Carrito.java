/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author josha
 */


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelos.CarritoItem;
import modelos.Usuario;

public class Carrito {
    private int id; // ID de la orden
    private Usuario usuario;
    private LocalDateTime fecha;
    private List<CarritoItem> items;

    public Carrito() {
        this.items = new ArrayList<>();
    }

    public Carrito(int id, Usuario usuario, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.items = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public List<CarritoItem> getItems() { return items; }
    public void setItems(List<CarritoItem> items) { this.items = items; }

    public void agregarItem(CarritoItem item) {
        this.items.add(item);
    }


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author josha
 */
public class CarritoItem {
    private Ropa ropa;
    private int cantidad;

    public CarritoItem() {}

    public CarritoItem(Ropa ropa, int cantidad) {
        this.ropa = ropa;
        this.cantidad = cantidad;
    }

    public Ropa getRopa() { return ropa; }
    public void setRopa(Ropa ropa) { this.ropa = ropa; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    
}

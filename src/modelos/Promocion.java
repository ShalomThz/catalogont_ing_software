/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author josha
 */



public class Promocion {
    private int id;
    private String descripcion;
    private double descuento;

    public Promocion(int id, String descripcion, double descuento) {
        this.id = id;
        this.descripcion = descripcion;
        this.descuento = descuento;
    }

    public int getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public double getDescuento() { return descuento; }
}

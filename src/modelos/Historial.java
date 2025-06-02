/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author josha
 */
import java.sql.Date;
import java.sql.Time;

public class Historial {
    private int id;
    private int idUsuario;
    private Date fecha;
    private Time hora;
    private String accion;
    private String descripcion;

    // Constructor vacío
    public Historial() {}

    // Constructor con parámetros
    public Historial(int idUsuario, Date fecha, Time hora, String accion, String descripcion) {
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.hora = hora;
        this.accion = accion;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

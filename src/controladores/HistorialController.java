package controladores;

import db.ConexionDB;
import modelos.Historial;
import modelos.Usuario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialController {

    public void registrarAccion(Usuario usuario, String accion, String descripcion) {
        String sql = "INSERT INTO historial (id_usuario, fecha, hora, accion, descripcion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Date fechaActual = Date.valueOf(LocalDate.now());
            Time horaActual = Time.valueOf(LocalTime.now());

            stmt.setInt(1, usuario.getId());
            stmt.setDate(2, fechaActual);
            stmt.setTime(3, horaActual);
            stmt.setString(4, accion);
            stmt.setString(5, descripcion);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Historial> obtenerHistorialPorUsuario(Usuario usuario) {
        List<Historial> historialList = new ArrayList<>();
        String sql = "SELECT * FROM historial WHERE id_usuario = ? ORDER BY fecha DESC, hora DESC";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Historial h = new Historial();
                h.setId(rs.getInt("id"));
                h.setUsuario(usuario);
                h.setFecha(rs.getDate("fecha"));
                h.setHora(rs.getTime("hora"));
                h.setAccion(rs.getString("accion"));
                h.setDescripcion(rs.getString("descripcion"));

                historialList.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historialList;
    }

    public List<Historial> obtenerTodoElHistorial() {
        List<Historial> historialList = new ArrayList<>();
        String sql = "SELECT h.*, u.nombre, u.email, u.contrasena, u.rol FROM historial h JOIN usuario u ON h.id_usuario = u.id ORDER BY h.fecha DESC, h.hora DESC";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Historial h = new Historial();
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol"));

                h.setId(rs.getInt("id"));
                h.setUsuario(u);
                h.setFecha(rs.getDate("fecha"));
                h.setHora(rs.getTime("hora"));
                h.setAccion(rs.getString("accion"));
                h.setDescripcion(rs.getString("descripcion"));

                historialList.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historialList;
    }

    public List<Historial> obtenerHistorialPorFecha(Date fecha) {
        List<Historial> historialList = new ArrayList<>();
        String sql = "SELECT h.*, u.nombre, u.email, u.contrasena, u.rol FROM historial h JOIN usuarios u ON h.id_usuario = u.id WHERE h.fecha = ? ORDER BY h.hora DESC";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, fecha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Historial h = new Historial();
                Usuario u = new Usuario();
                u.setId(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setContrasena(rs.getString("contrasena"));
                u.setRol(rs.getString("rol"));

                h.setId(rs.getInt("id"));
                h.setUsuario(u);
                h.setFecha(rs.getDate("fecha"));
                h.setHora(rs.getTime("hora"));
                h.setAccion(rs.getString("accion"));
                h.setDescripcion(rs.getString("descripcion"));

                historialList.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historialList;
    }
}

package controladores;

import db.ConexionDB;
import modelos.Usuario;
import seguridad.SeguridadUtil;

import java.sql.*;

public class UsuarioController {

    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, email, contrasena, rol) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = SeguridadUtil.hashSHA256(usuario.getContrasena());
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, hashedPassword);
            ps.setString(4, usuario.getRol());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Usuario autenticarUsuario(String nombre, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE nombre = ? AND contrasena = ?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = SeguridadUtil.hashSHA256(contrasena);
            ps.setString(1, nombre);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        "", // No devolver contrasena
                        rs.getString("rol")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void editarUsuarioPorNombre(Usuario usuario) {
        String sql = "UPDATE usuario SET email = ?, contrasena = ?, rol = ? WHERE nombre = ?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = SeguridadUtil.hashSHA256(usuario.getContrasena());
            ps.setString(1, usuario.getEmail());
            ps.setString(2, hashedPassword);
            ps.setString(3, usuario.getRol());
            ps.setString(4, usuario.getNombre());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún usuario con el nombre especificado.");
            }

        } catch (SQLException e) {
            System.err.println("Error al editar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminarUsuarioPorNombre(String nombre) {
        String sql = "DELETE FROM usuario WHERE nombre = ?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún usuario con el nombre especificado.");
            } else {
                System.out.println("Usuario eliminado exitosamente.");
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
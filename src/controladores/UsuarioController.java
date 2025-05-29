package controladores;

import db.ConexionDB;
import modelos.Usuario;
import seguridad.SeguridadUtil;

import java.sql.*;
/**
 *
 * @author josha
 */
public class UsuarioController {

    public void agregarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (username, email, contrasena) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = SeguridadUtil.hashSHA256(usuario.getContrasena());
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, hashedPassword);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Usuario autenticarUsuario(String username, String contrasena) {
        String sql = "SELECT * FROM usuario WHERE username = ? AND contrasena = ?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String hashedPassword = SeguridadUtil.hashSHA256(contrasena);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        "" // Evitar retornar la contraseña
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
    
    public void editarUsuarioPorUsername(Usuario usuario) {
    String sql = "UPDATE usuario SET email = ?, contrasena = ? WHERE username = ?";
    try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

        String hashedPassword = SeguridadUtil.hashSHA256(usuario.getContrasena());
        ps.setString(1, usuario.getEmail());
        ps.setString(2, hashedPassword);
        ps.setString(3, usuario.getUsername());

        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas == 0) {
            System.out.println("No se encontró ningún usuario con el username especificado.");
        }

    } catch (SQLException e) {
        System.err.println("Error al editar usuario: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    public void eliminarUsuarioPorUsername(String username) {
    String sql = "DELETE FROM usuario WHERE username = ?";
    try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, username);

        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas == 0) {
            System.out.println("No se encontró ningún usuario con el username especificado.");
        } else {
            System.out.println("Usuario eliminado exitosamente.");
        }

    } catch (SQLException e) {
        System.err.println("Error al eliminar usuario: " + e.getMessage());
        e.printStackTrace();
    }
}


}

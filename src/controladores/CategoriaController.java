package controladores;

import db.ConexionDB;
import modelos.Categoria;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author josha
 */

public class CategoriaController {

    public Categoria buscarPorNombre(String nombre) {
        String sql = "SELECT id, nombre, descripcion, foto FROM categoria WHERE nombre = ?";
        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("foto")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void agregarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre, descripcion, foto) VALUES (?, ?, ?)";

        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setString(3, categoria.getFoto());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarCategoria(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";

        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Categoria> obtenerCategorias() {
        ArrayList<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, foto FROM categoria";

        try (Connection con = ConexionDB.getConexion(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("foto")
                );
                lista.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean editarCategoriaPorNombre(String nombreActual, Categoria nuevaCategoria) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ?, foto = ? WHERE nombre = ?";

        try (Connection con = ConexionDB.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevaCategoria.getNombre());
            ps.setString(2, nuevaCategoria.getDescripcion());
            ps.setString(3, nuevaCategoria.getFoto());
            ps.setString(4, nombreActual);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

package controladores;

import db.ConexionDB;
import modelos.Categoria;

import java.sql.*;
import java.util.ArrayList;

public class CategoriaController {
    public Categoria buscarPorNombre(String nombre) {
    String sql = "SELECT id, nombre FROM categoria WHERE nombre = ?";
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Categoria(rs.getInt("id"), rs.getString("nombre"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    public void agregarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarCategoria(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    public ArrayList<Categoria> obtenerCategorias() {
        ArrayList<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion FROM categoria";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                lista.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

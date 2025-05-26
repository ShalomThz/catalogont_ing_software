package controladores;

import db.ConexionDB;
import modelos.Ropa;
import modelos.Categoria;

import java.sql.*;
import java.util.ArrayList;

public class RopaController {

    public void agregarRopa(Ropa ropa) {
        String sql = "INSERT INTO ropa (modelo, nombre, precio, talla, color, categoria_id, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ropa.getModelo());
            ps.setString(2, ropa.getNombre());
            ps.setDouble(3, ropa.getPrecio());
            ps.setString(4, ropa.getTalla());
            ps.setString(5, ropa.getColor());
            ps.setInt(6, ropa.getCategoria().getId());
            ps.setString(7, ropa.getImagen());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ropa> obtenerRopaPorCategoria(String nombreCategoria) {
        ArrayList<Ropa> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.modelo, r.nombre, r.precio, r.talla, r.color, r.imagen, " +
                     "c.id AS cat_id, c.nombre AS cat_nombre, c.descripcion AS cat_desc " +
                     "FROM ropa r JOIN categoria c ON r.categoria_id = c.id " +
                     "WHERE LOWER(c.nombre) = LOWER(?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreCategoria);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categoria cat = new Categoria(
                        rs.getInt("cat_id"),
                        rs.getString("cat_nombre"),
                        rs.getString("cat_desc")
                    );
                    Ropa r = new Ropa(
                        rs.getInt("id"),
                        rs.getString("modelo"),
                        rs.getString("nombre"),
                        cat,
                        rs.getString("talla"),
                        rs.getString("color"),
                        rs.getDouble("precio"),
                        rs.getString("imagen")
                    );
                    lista.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

public ArrayList<Ropa> obtenerRopa() {
    ArrayList<Ropa> lista = new ArrayList<>();
    String sql = "SELECT r.id, r.modelo, r.nombre, r.precio, r.talla, r.color, r.imagen, " +
                 "c.id AS cat_id, c.nombre AS cat_nombre, c.descripcion AS cat_descripcion, c.foto AS cat_foto " +
                 "FROM ropa r JOIN categoria c ON r.categoria_id = c.id";

    try (Connection con = ConexionDB.getConexion();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            Categoria categoria = new Categoria(
                rs.getInt("cat_id"),
                rs.getString("cat_nombre"),
                rs.getString("cat_descripcion"),
                rs.getString("cat_foto")
            );

            Ropa ropa = new Ropa(
                rs.getInt("id"),
                rs.getString("modelo"),
                rs.getString("nombre"),
                categoria,
                rs.getString("talla"),
                rs.getString("color"),
                rs.getDouble("precio"),
                rs.getString("imagen")
            );

            lista.add(ropa);
        }

    } catch (SQLException e) {
        System.err.println("Error al obtener la ropa: " + e.getMessage());
        e.printStackTrace();
    }

    return lista;
}

    
public void editarRopaPorId(Ropa ropa) {
    String sql = "UPDATE ropa SET nombre = ?, precio = ?, talla = ?, color = ?, categoria_id = ?, imagen = ? WHERE id = ?";
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, ropa.getNombre());
        ps.setDouble(2, ropa.getPrecio());
        ps.setString(3, ropa.getTalla());
        ps.setString(4, ropa.getColor());
        ps.setInt(5, ropa.getCategoria().getId());
        ps.setString(6, ropa.getImagen());
        ps.setInt(7, ropa.getId());

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace(); 
    }
}

//cuidadoooooo esto borra toda la ropa que tenga ese modelo
    public void eliminarRopaPorModelo(String modelo) {
    String sql = "DELETE FROM ropa WHERE modelo = ?";
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, modelo);
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace(); 
    }
}
    
    public void eliminarRopaPorId(int id) {
    String sql = "DELETE FROM ropa WHERE id = ?";
    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace(); 
    }
}

    public ArrayList<Ropa> buscarRopa(String nombre, String modelo, Double precio, String color) {
    ArrayList<Ropa> lista = new ArrayList<>();
    StringBuilder sql = new StringBuilder(
        "SELECT r.id, r.modelo, r.nombre, r.precio, r.talla, r.color, r.imagen, " +
        "c.id AS cat_id, c.nombre AS cat_nombre, c.descripcion AS cat_descripcion, c.foto AS cat_foto " +
        "FROM ropa r JOIN categoria c ON r.categoria_id = c.id WHERE 1=1"
    );

    ArrayList<Object> parametros = new ArrayList<>();

    if (nombre != null && !nombre.isEmpty()) {
        sql.append(" AND LOWER(r.nombre) LIKE ?");
        parametros.add("%" + nombre.toLowerCase() + "%");
    }
    if (modelo != null && !modelo.isEmpty()) {
        sql.append(" AND LOWER(r.modelo) LIKE ?");
        parametros.add("%" + modelo.toLowerCase() + "%");
    }
    if (precio != null) {
        sql.append(" AND r.precio = ?");
        parametros.add(precio);
    }
    if (color != null && !color.isEmpty()) {
        sql.append(" AND LOWER(r.color) LIKE ?");
        parametros.add("%" + color.toLowerCase() + "%");
    }

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        for (int i = 0; i < parametros.size(); i++) {
            ps.setObject(i + 1, parametros.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getInt("cat_id"),
                    rs.getString("cat_nombre"),
                    rs.getString("cat_descripcion"),
                    rs.getString("cat_foto")
                );

                Ropa ropa = new Ropa(
                    rs.getInt("id"),
                    rs.getString("modelo"),
                    rs.getString("nombre"),
                    categoria,
                    rs.getString("talla"),
                    rs.getString("color"),
                    rs.getDouble("precio"),
                    rs.getString("imagen")
                );

                lista.add(ropa);
            }
        }

    } catch (SQLException e) {
        System.err.println("Error al buscar ropa: " + e.getMessage());
        e.printStackTrace();
    }

    return lista;
}


}

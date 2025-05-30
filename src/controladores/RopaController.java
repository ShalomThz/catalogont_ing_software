package controladores;

import db.ConexionDB;
import modelos.Ropa;
import modelos.Categoria;

import java.sql.*;
import java.util.ArrayList;

public class RopaController {

    public void agregarRopa(Ropa ropa) {
        String sql = "INSERT INTO ropa (modelo, nombre, marca, precio, descuento, talla, color, disponible, categoria_id, imagen, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ropa.getModelo());
            ps.setString(2, ropa.getNombre());
            ps.setString(3, ropa.getMarca());
            ps.setDouble(4, ropa.getPrecio());
            ps.setDouble(5, ropa.getDescuento());
            ps.setString(6, ropa.getTalla());
            ps.setString(7, ropa.getColor());
            ps.setBoolean(8, ropa.isDisponible());
            ps.setInt(9, ropa.getCategoria().getId());
            ps.setString(10, ropa.getImagen());
            ps.setString(11, ropa.getDescripcion());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ropa> obtenerRopa() {
        ArrayList<Ropa> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.modelo, r.nombre, r.marca, r.precio, r.descuento, r.talla, r.color, r.disponible, r.imagen, r.descripcion, " +
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
                    rs.getString("marca"),
                    rs.getString("color"),
                    rs.getString("talla"),
                    rs.getDouble("precio"),
                    rs.getDouble("descuento"),
                    rs.getBoolean("disponible"),
                    rs.getString("descripcion"),
                    rs.getString("imagen"),
                    categoria
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
        String sql = "UPDATE ropa SET nombre = ?, marca = ?, precio = ?, descuento = ?, talla = ?, color = ?, disponible = ?, categoria_id = ?, imagen = ?, descripcion = ? WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ropa.getNombre());
            ps.setString(2, ropa.getMarca());
            ps.setDouble(3, ropa.getPrecio());
            ps.setDouble(4, ropa.getDescuento());
            ps.setString(5, ropa.getTalla());
            ps.setString(6, ropa.getColor());
            ps.setBoolean(7, ropa.isDisponible());
            ps.setInt(8, ropa.getCategoria().getId());
            ps.setString(9, ropa.getImagen());
            ps.setString(10, ropa.getDescripcion());
            ps.setInt(11, ropa.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

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
            "SELECT r.id, r.modelo, r.nombre, r.marca, r.precio, r.descuento, r.talla, r.color, r.disponible, r.imagen, r.descripcion, " +
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
                        rs.getString("marca"),
                        rs.getString("color"),
                        rs.getString("talla"),
                        rs.getDouble("precio"),
                        rs.getDouble("descuento"),
                        rs.getBoolean("disponible"),
                        rs.getString("descripcion"),
                        rs.getString("imagen"),
                        categoria
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

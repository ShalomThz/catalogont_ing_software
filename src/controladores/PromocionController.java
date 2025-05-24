/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

/**
 *
 * @author josha
 */


import db.ConexionDB;
import modelos.Promocion;

import java.sql.*;
import java.util.ArrayList;

public class PromocionController {

    public void agregarPromocion(Promocion promocion) {
        String sql = "INSERT INTO promociones (descripcion, descuento) VALUES (?, ?)";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, promocion.getDescripcion());
            ps.setDouble(2, promocion.getDescuento());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarPromocion(int id) {
        String sql = "DELETE FROM promociones WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Promocion> obtenerPromociones() {
        ArrayList<Promocion> lista = new ArrayList<>();
        String sql = "SELECT id, descripcion, descuento FROM promociones";

        try (Connection con = ConexionDB.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Promocion p = new Promocion(rs.getInt("id"), rs.getString("descripcion"), rs.getDouble("descuento"));
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}

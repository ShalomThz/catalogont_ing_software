package controladores;

import db.ConexionDB;
import modelos.Carrito;
import modelos.CarritoItem;
import modelos.Categoria;
import modelos.Ropa;
import modelos.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CarritoController {

    public Carrito obtenerCarritoUsuario(String nombreUsuario) {
        Carrito carrito = new Carrito();

        try (Connection conn = ConexionDB.getConexion()) {
            int ordenId = obtenerOcrearOrdenActiva(conn, nombreUsuario);

            carrito.setId(ordenId);
            carrito.setUsuario(obtenerUsuarioPorNombre(conn, nombreUsuario));
            carrito.setFecha(obtenerFechaOrden(conn, ordenId));

       String query = """
    SELECT r.id, r.modelo, r.nombre, r.marca, r.precio, r.descuento, r.talla, r.color, 
           r.disponible, r.descripcion, r.foto, r.categoria_id, orp.cantidad,
           c.nombre AS cat_nombre, c.descripcion AS cat_descripcion, c.foto AS cat_foto
    FROM orden_ropa orp
    JOIN ropa r ON r.id = orp.ropa_id
    JOIN categoria c ON r.categoria_id = c.id
    WHERE orp.orden_id = ?
""";


            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, ordenId);
                ResultSet rs = stmt.executeQuery();
                List<CarritoItem> items = new ArrayList<>();

                while (rs.next()) {
                    Categoria categoria = new Categoria(
                        rs.getInt("categoria_id"),
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
    rs.getBigDecimal("precio"),
    rs.getBigDecimal("descuento"),
    rs.getBoolean("disponible"),
    rs.getString("descripcion"),
    rs.getString("foto"), 
    categoria
);

                    int cantidad = rs.getInt("cantidad");
                    items.add(new CarritoItem(ropa, cantidad));
                }

                carrito.setItems(items);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carrito;
    }

    public void agregarAlCarrito(String nombreUsuario, Ropa ropa, int cantidad) {
        try (Connection conn = ConexionDB.getConexion()) {
            int ordenId = obtenerOcrearOrdenActiva(conn, nombreUsuario);

            String query = """
                INSERT INTO orden_ropa (orden_id, ropa_id, cantidad)
                VALUES (?, ?, ?)
                ON CONFLICT (orden_id, ropa_id)
                DO UPDATE SET cantidad = orden_ropa.cantidad + EXCLUDED.cantidad
            """;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, ordenId);
                stmt.setInt(2, ropa.getId());
                stmt.setInt(3, cantidad);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void quitarDelCarrito(String nombreUsuario, Ropa ropa) {
        try (Connection conn = ConexionDB.getConexion()) {
            int ordenId = obtenerOcrearOrdenActiva(conn, nombreUsuario);

            String query = "DELETE FROM orden_ropa WHERE orden_id = ? AND ropa_id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, ordenId);
                stmt.setInt(2, ropa.getId());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int obtenerOcrearOrdenActiva(Connection conn, String nombreUsuario) throws SQLException {
        String selectQuery = """
            SELECT o.id
            FROM orden o
            JOIN usuario u ON o.usuario_id = u.id
            WHERE u.nombre = ?
            ORDER BY o.fecha DESC
            LIMIT 1
        """;

        try (PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        // Si no existe, se crea una nueva orden para el usuario
        Usuario usuario = obtenerUsuarioPorNombre(conn, nombreUsuario);
        if (usuario == null) throw new SQLException("Usuario no encontrado: " + nombreUsuario);

        String insertQuery = "INSERT INTO orden (usuario_id, fecha) VALUES (?, CURRENT_TIMESTAMP) RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        throw new SQLException("No se pudo crear una orden nueva para el usuario.");
    }

    private Usuario obtenerUsuarioPorNombre(Connection conn, String nombreUsuario) throws SQLException {
        String query = "SELECT id, nombre FROM usuario WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                return usuario;
            }
        }
        return null;
    }

    private LocalDateTime obtenerFechaOrden(Connection conn, int ordenId) throws SQLException {
        String query = "SELECT fecha FROM orden WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ordenId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("fecha");
                return timestamp != null ? timestamp.toLocalDateTime() : null;
            }
        }
        return null;
    }
}

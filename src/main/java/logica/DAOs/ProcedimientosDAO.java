package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;

import java.sql.*;

public class ProcedimientosDAO {

    private final Connection conexion;

    public ProcedimientosDAO(ConexionBaseDeDatos conexionBD) {
        this.conexion = conexionBD.getConnection();
    }


    public void registrarCompra(int idProducto, int idDueno, Timestamp fecha) {
        String sql = "{CALL registrarCompra(?, ?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idDueno);
            stmt.setTimestamp(3, fecha);
            stmt.execute();
            System.out.println("Compra registrada exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al registrar la compra: " + e.getMessage());
        }
    }


    public void verificarVeterinariosDisponibles(Timestamp fechaDeseada) {
        String sql = "{CALL verificarVeterinariosDisponibles(?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setTimestamp(1, fechaDeseada);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Veterinario disponible: " + rs.getString("nombre") + " " + rs.getString("apellidos"));
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar disponibilidad de veterinarios: " + e.getMessage());
        }
    }


    public int contarProductosPorNombre(String nombreProducto) {
        String sql = "SELECT contarProductos(?) AS total";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al contar productos: " + e.getMessage());
        }
        return 0;
    }


    public int contarCitasPorMes(int idVet, int mes, int anio) {
        String sql = "SELECT contarCitasPorMes(?, ?, ?) AS total";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idVet);
            stmt.setInt(2, mes);
            stmt.setInt(3, anio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error al contar citas: " + e.getMessage());
        }
        return 0;
    }
}

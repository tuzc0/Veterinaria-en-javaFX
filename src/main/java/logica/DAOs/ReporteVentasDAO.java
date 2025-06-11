package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import java.sql.*;

public class ReporteVentasDAO {
    private final Connection conexion;

    public ReporteVentasDAO(ConexionBaseDeDatos conexionBD) {
        this.conexion = conexionBD.getConnection();
    }

    public String obtenerProductoMasVendido(int mes, int anio) {
        String sql = "{CALL productoMasVendidoMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "Producto más vendido: " + rs.getString("nombre")
                        + "\nVentas: " + rs.getInt("total_ventas");
            }
            return "No se encontraron ventas para ese mes.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String obtenerProductoMenosVendido(int mes, int anio) {
        String sql = "{CALL productoMenosVendidoMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "Producto menos vendido: " + rs.getString("nombre")
                        + "\nVentas: " + rs.getInt("total_ventas");
            }
            return "No se encontraron ventas para ese mes.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String obtenerPromedioVentas(int mes, int anio) {
        String sql = "{CALL promedioVentasMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return String.format("Promedio de ventas: %.2f", rs.getDouble("promedio_ventas"));
            }
            return "No hay ventas registradas en ese mes.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String obtenerSumaTotalVentas(int mes, int anio) {
        String sql = "{CALL sumaTotalVentasMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "Suma total de ventas: " + rs.getInt("suma_total_ventas");
            }
            return "No se encontraron ventas.";
        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}
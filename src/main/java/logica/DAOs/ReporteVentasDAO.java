package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReporteVentasDAO {

    private final Connection conexion;

    public ReporteVentasDAO(ConexionBaseDeDatos conexionBD) {
        this.conexion = conexionBD.getConnection();
    }

    public void obtenerProductoMasVendido(int mes, int anio) {
        String sql = "{CALL productoMasVendidoMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Producto más vendido: " + rs.getString("nombre")
                        + " - Ventas: " + rs.getInt("total_ventas"));
            } else {
                System.out.println("No se encontraron ventas para ese mes.");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener producto más vendido: " + e.getMessage());
        }
    }

    public void obtenerProductoMenosVendido(int mes, int anio) {
        String sql = "{CALL productoMenosVendidoMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Producto menos vendido: " + rs.getString("nombre")
                        + " - Ventas: " + rs.getInt("total_ventas"));
            } else {
                System.out.println("No se encontraron ventas para ese mes.");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener producto menos vendido: " + e.getMessage());
        }
    }

    public void obtenerPromedioVentas(int mes, int anio) {
        String sql = "{CALL promedioVentasMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Promedio de ventas: " + rs.getDouble("promedio_ventas"));
            } else {
                System.out.println("No hay ventas registradas en ese mes.");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener el promedio de ventas: " + e.getMessage());
        }
    }

    public void obtenerSumaTotalVentas(int mes, int anio) {
        String sql = "{CALL sumaTotalVentasMes(?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, mes);
            stmt.setInt(2, anio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Suma total de ventas: " + rs.getInt("suma_total_ventas"));
            } else {
                System.out.println("No se encontraron ventas.");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener suma total de ventas: " + e.getMessage());
        }
    }
}

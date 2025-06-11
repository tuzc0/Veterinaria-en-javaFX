package logica.DAOs;

import GUIauxiliar.Utilidades;
import accesoadatos.ConexionBaseDeDatos;

import java.sql.*;

public class ProcedimientosDAO {

    private final Connection conexion;

    public ProcedimientosDAO(ConexionBaseDeDatos conexionBD) {
        this.conexion = conexionBD.getConnection();
    }

    Utilidades utilidades = new Utilidades();


    public void registrarCompra(int idProducto, int idDueno, Timestamp fecha, int cantidad) {
        String sql = "{CALL registrarCompra(?, ?, ?, ?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idDueno);
            stmt.setTimestamp(3, fecha);
            stmt.setInt(4, cantidad);

            stmt.execute();

            utilidades.mostrarAlerta(
                    "Compra Registrada",
                    "La compra se ha registrado correctamente.",
                    "Se compraron " + cantidad + " unidades."
            );
        } catch (SQLException e) {
            utilidades.mostrarAlerta(
                    "Error al Registrar Compra",
                    "No fue posible registrar la compra.",
                    "Verifica la existencia del producto o intenta nuevamente.\n" +
                            "Detalle: " + e.getMessage()
            );
        }
    }



    public void verificarVeterinariosDisponibles(Timestamp fechaDeseada) {
        String sql = "{CALL verificarVeterinariosDisponibles(?)}";
        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            stmt.setTimestamp(1, fechaDeseada);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                utilidades.mostrarAlerta(
                        "Veterinario Disponible",
                        "Veterinario: " + rs.getString("nombre") + " " + rs.getString("apellido"),
                        "Fecha y hora disponibles: " + rs.getTimestamp("fecha_hora")
                );
            }
        } catch (SQLException e) {
            utilidades.mostrarAlerta(
                    "Error al Verificar Disponibilidad",
                    "Ocurrió un error al verificar la disponibilidad de los veterinarios.",
                    "Por favor, intente nuevamente."
            );
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
            utilidades.mostrarAlerta(
                    "Error al Contar Productos",
                    "Ocurrió un error al contar los productos.",
                    "Por favor, intente nuevamente."
            );
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
            utilidades.mostrarAlerta(
                    "Error al Contar Citas",
                    "Ocurrió un error al contar las citas del veterinario.",
                    "Por favor, intente nuevamente."
            );
        }
        return 0;
    }
}

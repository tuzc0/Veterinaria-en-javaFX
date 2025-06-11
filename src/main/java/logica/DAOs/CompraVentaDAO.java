package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CompraVentaDAO {

    private Connection conexionBaseDeDatos;

    public boolean realizarCompraVenta(int clienteId, List<Integer> productoIds, List<Integer> cantidades, double total) throws SQLException, IOException {
        String insertarCompraSQL = "INSERT INTO compra_venta (cliente_id, fecha, total) VALUES (?, NOW(), ?)";
        String actualizarExistenciaSQL = "UPDATE producto SET existencia = existencia - ? WHERE idProducto = ?";

        PreparedStatement consultaCompra = null;
        PreparedStatement consultaProducto = null;

        boolean transaccionExitosa = false;

        try {
            // Obtener conexión
            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            conexionBaseDeDatos.setAutoCommit(false); // Desactivar autocommit

            // Insertar la compra-venta
            consultaCompra = conexionBaseDeDatos.prepareStatement(insertarCompraSQL);
            consultaCompra.setInt(1, clienteId);
            consultaCompra.setDouble(2, total);
            consultaCompra.executeUpdate();

            // Actualizar la existencia de los productos
            consultaProducto = conexionBaseDeDatos.prepareStatement(actualizarExistenciaSQL);
            for (int i = 0; i < productoIds.size(); i++) {
                consultaProducto.setInt(1, cantidades.get(i));
                consultaProducto.setInt(2, productoIds.get(i));
                consultaProducto.executeUpdate();
            }

            // Confirmar la transacción
            conexionBaseDeDatos.commit();
            transaccionExitosa = true;

        } catch (SQLException e) {
            // Deshacer la transacción en caso de error
            if (conexionBaseDeDatos != null) {
                conexionBaseDeDatos.rollback();
            }
            throw e;
        } finally {
            // Cerrar recursos
            if (consultaCompra != null) consultaCompra.close();
            if (consultaProducto != null) consultaProducto.close();
            if (conexionBaseDeDatos != null) {
                conexionBaseDeDatos.setAutoCommit(true); // Restaurar autocommit
                conexionBaseDeDatos.close();
            }
        }

        return transaccionExitosa;
    }
}

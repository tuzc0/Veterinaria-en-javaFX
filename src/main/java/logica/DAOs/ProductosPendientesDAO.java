package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.ProductosPendientesDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosPendientesDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarProductoPendiente(ProductosPendientesDTO productoPendiente) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO productos_pendientes (idProducto, fechaRegistro) VALUES (?, ?)";
        boolean productoPendienteInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, productoPendiente.getIdProducto());
            consultaPreparada.setTimestamp(2, productoPendiente.getFechaRegistro());
            consultaPreparada.executeUpdate();
            productoPendienteInsertado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productoPendienteInsertado;
    }

    public boolean eliminarProductoPendientePorId(int idProducto) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM productos_pendientes WHERE idProducto = ?";
        boolean productoPendienteEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idProducto);
            consultaPreparada.executeUpdate();
            productoPendienteEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productoPendienteEliminado;
    }

    public ProductosPendientesDTO buscarProductoPendientePorId(int idProducto) throws SQLException, IOException {

        ProductosPendientesDTO productoPendiente = new ProductosPendientesDTO(-1, null);
        String consultaSQL = "SELECT * FROM productos_pendientes WHERE idProducto = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idProducto);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                productoPendiente = new ProductosPendientesDTO(
                        resultadoConsulta.getInt("idProducto"),
                        resultadoConsulta.getTimestamp("fechaRegistro")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productoPendiente;
    }

    public List<ProductosPendientesDTO> listarProductosPendientes() throws SQLException, IOException {

        List<ProductosPendientesDTO> productosPendientes = new ArrayList<>();
        String consultaSQL = "SELECT * FROM productos_pendientes";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                ProductosPendientesDTO productoPendiente = new ProductosPendientesDTO(
                        resultadoConsulta.getInt("idProducto"),
                        resultadoConsulta.getTimestamp("fechaRegistro")
                );

                productosPendientes.add(productoPendiente);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productosPendientes;
    }
}

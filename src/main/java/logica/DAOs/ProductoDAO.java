package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.ProductoDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarProducto(ProductoDTO producto) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO producto (idProducto, nombre, precio, especie, tipo, marca, existencia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean productoInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, producto.getIdProducto());
            consultaPreparada.setString(2, producto.getNombre());
            consultaPreparada.setDouble(3, producto.getPrecio());
            consultaPreparada.setString(4, producto.getEspecie());
            consultaPreparada.setString(5, producto.getTipo());
            consultaPreparada.setString(6, producto.getMarca());
            consultaPreparada.setInt(7, producto.getExistencia());
            consultaPreparada.executeUpdate();
            productoInsertado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productoInsertado;
    }

    public boolean eliminarProductoPorId(int idProducto) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM producto WHERE idProducto = ?";
        boolean productoEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idProducto);
            consultaPreparada.executeUpdate();
            productoEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productoEliminado;
    }

    public boolean modificarProducto(ProductoDTO producto) throws SQLException, IOException {

        String consultaSQL = "UPDATE producto SET nombre = ?, precio = ?, especie = ?, tipo = ?, marca = ?, existencia = ? WHERE idProducto = ?";
        boolean productoModificado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, producto.getNombre());
            consultaPreparada.setDouble(2, producto.getPrecio());
            consultaPreparada.setString(3, producto.getEspecie());
            consultaPreparada.setString(4, producto.getTipo());
            consultaPreparada.setString(5, producto.getMarca());
            consultaPreparada.setInt(6, producto.getExistencia());
            consultaPreparada.setInt(7, producto.getIdProducto());
            consultaPreparada.executeUpdate();
            productoModificado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productoModificado;
    }

    public ProductoDTO buscarProductoPorId(int idProducto) throws SQLException, IOException {

        ProductoDTO producto = new ProductoDTO(-1, "N/A", 0.0, "N/A", "N/A", "N/A", -1);
        String consultaSQL = "SELECT * FROM producto WHERE idProducto = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idProducto);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                producto = new ProductoDTO(
                        resultadoConsulta.getInt("idProducto"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getDouble("precio"),
                        resultadoConsulta.getString("especie"),
                        resultadoConsulta.getString("tipo"),
                        resultadoConsulta.getString("marca"),
                        resultadoConsulta.getInt("existencia")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return producto;
    }

    public List<ProductoDTO> listarProductos() throws SQLException, IOException {

        List<ProductoDTO> productos = new ArrayList<>();
        String consultaSQL = "SELECT * FROM producto";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                ProductoDTO producto = new ProductoDTO(
                        resultadoConsulta.getInt("idProducto"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getDouble("precio"),
                        resultadoConsulta.getString("especie"),
                        resultadoConsulta.getString("tipo"),
                        resultadoConsulta.getString("marca"),
                        resultadoConsulta.getInt("existencia")
                );

                productos.add(producto);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return productos;
    }

    public int validarDisponibilidadProducto(int idProducto) throws SQLException, IOException {
        int existencia = -1;
        String consultaSQL = "SELECT existencia FROM producto WHERE idProducto = ?";

        try {
            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idProducto);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {
                existencia = resultadoConsulta.getInt("existencia");
            } else {
                throw new SQLException("El producto con ID " + idProducto + " no existe.");
            }
        } finally {
            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return existencia;
    }
}
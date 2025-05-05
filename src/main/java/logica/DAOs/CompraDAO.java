package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.CompraDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarCompra(CompraDTO compra) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO compra (idProducto, idDueño, fechaCompra) VALUES (?, ?, ?)";
        boolean compraInsertada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, compra.getIdProducto());
            consultaPreparada.setInt(2, compra.getIdDueño());
            consultaPreparada.setTimestamp(3, compra.getFechaCompra());
            consultaPreparada.executeUpdate();
            compraInsertada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return compraInsertada;
    }

    public boolean eliminarCompraPorId(int idProducto) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM compra WHERE idProducto = ?";
        boolean compraEliminada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idProducto);
            consultaPreparada.executeUpdate();
            compraEliminada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return compraEliminada;
    }

    public boolean modificarCompra(CompraDTO compra) throws SQLException, IOException {

        String consultaSQL = "UPDATE compra SET idDueño = ?, fechaCompra = ? WHERE idProducto = ?";
        boolean compraModificada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, compra.getIdDueño());
            consultaPreparada.setTimestamp(2, compra.getFechaCompra());
            consultaPreparada.setInt(3, compra.getIdProducto());
            consultaPreparada.executeUpdate();
            compraModificada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return compraModificada;
    }

    public CompraDTO buscarCompraPorId(int idProducto) throws SQLException, IOException {

        CompraDTO compra = new CompraDTO(-1, -1, null);
        String consultaSQL = "SELECT * FROM compra WHERE idProducto = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idProducto);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                compra = new CompraDTO(
                        resultadoConsulta.getInt("idProducto"),
                        resultadoConsulta.getInt("idDueño"),
                        resultadoConsulta.getTimestamp("fechaCompra")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return compra;
    }

    public List<CompraDTO> listarCompras() throws SQLException, IOException {

        List<CompraDTO> compras = new ArrayList<>();
        String consultaSQL = "SELECT * FROM compra";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                CompraDTO compra = new CompraDTO(
                        resultadoConsulta.getInt("idProducto"),
                        resultadoConsulta.getInt("idDueño"),
                        resultadoConsulta.getTimestamp("fechaCompra")
                );

                compras.add(compra);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return compras;
    }
}

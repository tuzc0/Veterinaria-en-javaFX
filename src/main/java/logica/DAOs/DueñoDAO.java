package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.DueñoDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DueñoDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarDueño(DueñoDTO dueño) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO dueño (idDueño, nombre, apellidos, calle, numero, colonia) VALUES (?, ?, ?, ?, ?, ?)";
        boolean dueñoInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, dueño.getIdDueño());
            consultaPreparada.setString(2, dueño.getNombre());
            consultaPreparada.setString(3, dueño.getApellidos());
            consultaPreparada.setString(4, dueño.getCalle());
            consultaPreparada.setString(5, dueño.getNumero());
            consultaPreparada.setString(6, dueño.getColonia());
            consultaPreparada.executeUpdate();
            dueñoInsertado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return dueñoInsertado;
    }

    public boolean eliminarDueñoPorId(int idDueño) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM dueño WHERE idDueño = ?";
        boolean dueñoEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idDueño);
            consultaPreparada.executeUpdate();
            dueñoEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return dueñoEliminado;
    }

    public boolean modificarDueño(DueñoDTO dueño) throws SQLException, IOException {

        String consultaSQL = "UPDATE dueño SET nombre = ?, apellidos = ?, calle = ?, numero = ?, colonia = ? WHERE idDueño = ?";
        boolean dueñoModificado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, dueño.getNombre());
            consultaPreparada.setString(2, dueño.getApellidos());
            consultaPreparada.setString(3, dueño.getCalle());
            consultaPreparada.setString(4, dueño.getNumero());
            consultaPreparada.setString(5, dueño.getColonia());
            consultaPreparada.setInt(6, dueño.getIdDueño());
            consultaPreparada.executeUpdate();
            dueñoModificado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return dueñoModificado;
    }

    public DueñoDTO buscarDueñoPorId(int idDueño) throws SQLException, IOException {

        DueñoDTO dueño = new DueñoDTO(-1, "N/A", "N/A", "N/A", "N/A", "N/A");
        String consultaSQL = "SELECT * FROM dueño WHERE idDueño = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idDueño);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                dueño = new DueñoDTO(
                        resultadoConsulta.getInt("idDueño"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("calle"),
                        resultadoConsulta.getString("numero"),
                        resultadoConsulta.getString("colonia")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return dueño;
    }

    public DueñoDTO buscarDueñoPorNombre(String nombre) throws SQLException, IOException {

        DueñoDTO dueño = new DueñoDTO(-1, "N/A", "N/A", "N/A", "N/A", "N/A");
        String consultaSQL = "SELECT * FROM dueño WHERE nombre = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, nombre);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                dueño = new DueñoDTO(
                        resultadoConsulta.getInt("idDueño"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("calle"),
                        resultadoConsulta.getString("numero"),
                        resultadoConsulta.getString("colonia")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return dueño;
    }

    public List<DueñoDTO> listarDueños() throws SQLException, IOException {

        List<DueñoDTO> dueños = new ArrayList<>();
        String consultaSQL = "SELECT * FROM dueño";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                DueñoDTO dueño = new DueñoDTO(
                        resultadoConsulta.getInt("idDueño"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("calle"),
                        resultadoConsulta.getString("numero"),
                        resultadoConsulta.getString("colonia")
                );

                dueños.add(dueño);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return dueños;
    }
}
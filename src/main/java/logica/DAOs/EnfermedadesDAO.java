package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.EnfermedadesDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadesDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarEnfermedad(EnfermedadesDTO enfermedad) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO enfermedades (idEnfermedad, nombreEnfermedad, especie, tratamiento) VALUES (?, ?, ?, ?)";
        boolean enfermedadInsertada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, enfermedad.getIdEnfermedad());
            consultaPreparada.setString(2, enfermedad.getNombre());
            consultaPreparada.setString(3, enfermedad.getEspecie());
            consultaPreparada.setString(4, enfermedad.getTratamiento());
            consultaPreparada.executeUpdate();
            enfermedadInsertada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return enfermedadInsertada;
    }

    public boolean eliminarEnfermedadPorId(int idEnfermedad) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM enfermedades WHERE idEnfermedad = ?";
        boolean enfermedadEliminada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idEnfermedad);
            consultaPreparada.executeUpdate();
            enfermedadEliminada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return enfermedadEliminada;
    }

    public boolean modificarEnfermedad(EnfermedadesDTO enfermedad) throws SQLException, IOException {

        String consultaSQL = "UPDATE enfermedades SET nombreEnfermedad = ?, especie = ?, tratamiento = ? WHERE idEnfermedad = ?";
        boolean enfermedadModificada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, enfermedad.getNombre());
            consultaPreparada.setString(2, enfermedad.getEspecie());
            consultaPreparada.setString(3, enfermedad.getTratamiento());
            consultaPreparada.setInt(4, enfermedad.getIdEnfermedad());
            consultaPreparada.executeUpdate();
            enfermedadModificada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return enfermedadModificada;
    }

    public EnfermedadesDTO buscarEnfermedadPorId(int idEnfermedad) throws SQLException, IOException {

        EnfermedadesDTO enfermedad = new EnfermedadesDTO(-1, "N/A", "N/A", "N/A");
        String consultaSQL = "SELECT * FROM enfermedades WHERE idEnfermedad = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idEnfermedad);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                enfermedad = new EnfermedadesDTO(
                        resultadoConsulta.getInt("idEnfermedad"),
                        resultadoConsulta.getString("nombreEnfermedad"),
                        resultadoConsulta.getString("especie"),
                        resultadoConsulta.getString("tratamiento")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return enfermedad;
    }

    public EnfermedadesDTO buscarEnfermedadPorNombre(String nombreEnfermedad) throws SQLException, IOException {

        EnfermedadesDTO enfermedad = new EnfermedadesDTO(-1, "N/A", "N/A", "N/A");
        String consultaSQL = "SELECT * FROM enfermedades WHERE nombreEnfermedad = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, nombreEnfermedad);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                enfermedad = new EnfermedadesDTO(
                        resultadoConsulta.getInt("idEnfermedad"),
                        resultadoConsulta.getString("nombreEnfermedad"),
                        resultadoConsulta.getString("especie"),
                        resultadoConsulta.getString("tratamiento")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return enfermedad;
    }

    public List<EnfermedadesDTO> listarEnfermedades() throws SQLException, IOException {

        List<EnfermedadesDTO> enfermedades = new ArrayList<>();
        String consultaSQL = "SELECT * FROM enfermedades";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                EnfermedadesDTO enfermedad = new EnfermedadesDTO(
                        resultadoConsulta.getInt("idEnfermedad"),
                        resultadoConsulta.getString("nombreEnfermedad"),
                        resultadoConsulta.getString("especie"),
                        resultadoConsulta.getString("tratamiento")
                );

                enfermedades.add(enfermedad);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return enfermedades;
    }
}
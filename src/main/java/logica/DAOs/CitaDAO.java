package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.CitaDTO;
import logica.DTOs.Estatus;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarCita(CitaDTO cita) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO cita (idCita, fecha, tipoCita, motivoCita, idAnimal, idSecretario, idVeterinario, estatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean citaInsertada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, cita.getIdCita());
            consultaPreparada.setTimestamp(2, cita.getFecha());
            consultaPreparada.setString(3, cita.getTipo());
            consultaPreparada.setString(4, cita.getMotivo());
            consultaPreparada.setInt(5, cita.getIdAnimal());
            consultaPreparada.setInt(6, cita.getIdSecretario());
            consultaPreparada.setInt(7, cita.getIdVeterinario());
            consultaPreparada.setString(8, cita.getEstatus().name());
            consultaPreparada.executeUpdate();
            citaInsertada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return citaInsertada;
    }

    public boolean eliminarCitaPorId(int idCita) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM cita WHERE idCita = ?";
        boolean citaEliminada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idCita);
            consultaPreparada.executeUpdate();
            citaEliminada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return citaEliminada;
    }

    public boolean modificarCita(CitaDTO cita) throws SQLException, IOException {

        String consultaSQL = "UPDATE cita SET fecha = ?, tipoCita = ?, motivoCita = ?, idAnimal = ?, idSecretario = ?, idVeterinario = ?, estatus = ? WHERE idCita = ?";
        boolean citaModificada = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setTimestamp(1, cita.getFecha());
            consultaPreparada.setString(2, cita.getTipo());
            consultaPreparada.setString(3, cita.getMotivo());
            consultaPreparada.setInt(4, cita.getIdAnimal());
            consultaPreparada.setInt(5, cita.getIdSecretario());
            consultaPreparada.setInt(6, cita.getIdVeterinario());
            consultaPreparada.setString(7, cita.getEstatus().name());
            consultaPreparada.setInt(8, cita.getIdCita());
            consultaPreparada.executeUpdate();
            citaModificada = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return citaModificada;
    }

    public CitaDTO buscarCitaPorId(int idCita) throws SQLException, IOException {

        CitaDTO cita = new CitaDTO(-1, null, "N/A", "N/A", -1, -1, -1, Estatus.Cancelada);
        String consultaSQL = "SELECT * FROM cita WHERE idCita = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idCita);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                cita = new CitaDTO(
                        resultadoConsulta.getInt("idCita"),
                        resultadoConsulta.getTimestamp("fecha"),
                        resultadoConsulta.getString("tipoCita"),
                        resultadoConsulta.getString("motivoCita"),
                        resultadoConsulta.getInt("idAnimal"),
                        resultadoConsulta.getInt("idSecretario"),
                        resultadoConsulta.getInt("idVeterinario"),
                        Estatus.valueOf(resultadoConsulta.getString("estatus"))
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return cita;
    }

    public List<CitaDTO> listarCitas() throws SQLException, IOException {

        List<CitaDTO> citas = new ArrayList<>();
        String consultaSQL = "SELECT * FROM cita";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                CitaDTO cita = new CitaDTO(
                        resultadoConsulta.getInt("idCita"),
                        resultadoConsulta.getTimestamp("fecha"),
                        resultadoConsulta.getString("tipoCita"),
                        resultadoConsulta.getString("motivoCita"),
                        resultadoConsulta.getInt("idAnimal"),
                        resultadoConsulta.getInt("idSecretario"),
                        resultadoConsulta.getInt("idVeterinario"),
                        Estatus.valueOf(resultadoConsulta.getString("estatus"))
                );

                citas.add(cita);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return citas;
    }

    public List<CitaDTO> obtenerCitasPorFecha(java.sql.Date fecha) throws SQLException, IOException {
        List<CitaDTO> citas = new ArrayList<>();
        String consultaSQL = "SELECT * FROM cita WHERE DATE(fecha) = ?";

        try {
            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setDate(1, fecha);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {
                CitaDTO cita = new CitaDTO(
                        resultadoConsulta.getInt("idCita"),
                        resultadoConsulta.getTimestamp("fecha"),
                        resultadoConsulta.getString("tipoCita"),
                        resultadoConsulta.getString("motivoCita"),
                        resultadoConsulta.getInt("idAnimal"),
                        resultadoConsulta.getInt("idSecretario"),
                        resultadoConsulta.getInt("idVeterinario"),
                        Estatus.valueOf(resultadoConsulta.getString("estatus"))
                );
                citas.add(cita);
            }
        } finally {
            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return citas;
    }
}

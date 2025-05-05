package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.DiagnosticaDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticaDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarDiagnostico(DiagnosticaDTO diagnostico) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO diagnostica (idCita, idEnfermedad) VALUES (?, ?)";
        boolean diagnosticoInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, diagnostico.getIdCita());
            consultaPreparada.setInt(2, diagnostico.getIdEnfermedad());
            consultaPreparada.executeUpdate();
            diagnosticoInsertado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return diagnosticoInsertado;
    }

    public boolean eliminarDiagnosticoPorId(int idCita) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM diagnostica WHERE idCita = ?";
        boolean diagnosticoEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idCita);
            consultaPreparada.executeUpdate();
            diagnosticoEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return diagnosticoEliminado;
    }

    public boolean modificarDiagnostico(DiagnosticaDTO diagnostico) throws SQLException, IOException {

        String consultaSQL = "UPDATE diagnostica SET idEnfermedad = ? WHERE idCita = ?";
        boolean diagnosticoModificado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, diagnostico.getIdEnfermedad());
            consultaPreparada.setInt(2, diagnostico.getIdCita());
            consultaPreparada.executeUpdate();
            diagnosticoModificado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return diagnosticoModificado;
    }

    public DiagnosticaDTO buscarDiagnosticoPorId(int idCita) throws SQLException, IOException {

        DiagnosticaDTO diagnostico = new DiagnosticaDTO(-1, -1);
        String consultaSQL = "SELECT * FROM diagnostica WHERE idCita = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idCita);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                diagnostico = new DiagnosticaDTO(
                        resultadoConsulta.getInt("idCita"),
                        resultadoConsulta.getInt("idEnfermedad")
                );
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return diagnostico;
    }

    public List<DiagnosticaDTO> listarDiagnosticos() throws SQLException, IOException {

        List<DiagnosticaDTO> diagnosticos = new ArrayList<>();
        String consultaSQL = "SELECT * FROM diagnostica";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                DiagnosticaDTO diagnostico = new DiagnosticaDTO(
                        resultadoConsulta.getInt("idCita"),
                        resultadoConsulta.getInt("idEnfermedad")
                );

                diagnosticos.add(diagnostico);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return diagnosticos;
    }
}
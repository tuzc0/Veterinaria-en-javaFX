package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.SecretarioDTO;
import logica.DTOs.VeterinarioDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarVeterinario(VeterinarioDTO veterinario) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO veterinario (idVeterinario, numeroCedulaProfesional, nombre, apellidos, telefonoCelular, telefonoEmergencia, usuario, contraseña, calle, numero, colonia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean veterinarioInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, veterinario.getIdVeterinario());
            consultaPreparada.setString(2, veterinario.getNumeroCedulaProfesional());
            consultaPreparada.setString(3, veterinario.getNombre());
            consultaPreparada.setString(4, veterinario.getApellidos());
            consultaPreparada.setString(5, veterinario.getTelefonoCelular());
            consultaPreparada.setString(6, veterinario.getTelefonoDeEmergencia());
            consultaPreparada.setString(7, veterinario.getUsuario());
            consultaPreparada.setString(8, veterinario.getContrasena());
            consultaPreparada.setString(9, veterinario.getCalle());
            consultaPreparada.setString(10, veterinario.getNumero());
            consultaPreparada.setString(11, veterinario.getColonia());
            consultaPreparada.executeUpdate();
            veterinarioInsertado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return veterinarioInsertado;
    }

    public boolean eliminarVeterinarioPorId(int idVeterinario) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM veterinario WHERE idVeterinario = ?";
        boolean veterinarioEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idVeterinario);
            consultaPreparada.executeUpdate();
            veterinarioEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return veterinarioEliminado;
    }

    public VeterinarioDTO buscarVeterinarioPorId(int idVeterinario) throws SQLException, IOException {

        VeterinarioDTO veterinario = new VeterinarioDTO(-1, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
        String consultaSQL = "SELECT * FROM veterinario WHERE idVeterinario = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idVeterinario);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                veterinario = new VeterinarioDTO(
                        resultadoConsulta.getInt("idVeterinario"),
                        resultadoConsulta.getString("numeroCedulaProfesional"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("telefonoCelular"),
                        resultadoConsulta.getString("telefonoEmergencia"),
                        resultadoConsulta.getString("usuario"),
                        resultadoConsulta.getString("contraseña"),
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

        return veterinario;
    }

    public VeterinarioDTO buscarVeterinarioPorUsuarioYContraseña(String usuario, String contraseña) throws SQLException, IOException {

        VeterinarioDTO veterinario = new VeterinarioDTO(-1, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
        String consultaSQL = "SELECT * FROM veterinario WHERE usuario = ? AND contraseña = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, usuario);
            consultaPreparada.setString(2, contraseña);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                veterinario = new VeterinarioDTO(
                        resultadoConsulta.getInt("idVeterinario"),
                        resultadoConsulta.getString("numeroCedulaProfesional"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("telefonoCelular"),
                        resultadoConsulta.getString("telefonoEmergencia"),
                        resultadoConsulta.getString("usuario"),
                        resultadoConsulta.getString("contraseña"),
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

        return veterinario;
    }

    public List<VeterinarioDTO> listarVeterinarios() throws SQLException, IOException {

        List<VeterinarioDTO> veterinarios = new ArrayList<>();
        String consultaSQL = "SELECT * FROM veterinario";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                VeterinarioDTO veterinario = new VeterinarioDTO(
                        resultadoConsulta.getInt("idVeterinario"),
                        resultadoConsulta.getString("numeroCedulaProfesional"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("telefonoCelular"),
                        resultadoConsulta.getString("telefonoEmergencia"),
                        resultadoConsulta.getString("usuario"),
                        resultadoConsulta.getString("contraseña"),
                        resultadoConsulta.getString("calle"),
                        resultadoConsulta.getString("numero"),
                        resultadoConsulta.getString("colonia")
                );

                veterinarios.add(veterinario);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return veterinarios;
    }

    public List<VeterinarioDTO> obtenerVeterinariosDisponibles(Timestamp fechaHora) throws SQLException, IOException {
        List<VeterinarioDTO> veterinarios = new ArrayList<>();
        String consultaSQL = "{CALL verificarVeterinariosDisponibles(?)}";

        try {
            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareCall(consultaSQL);
            consultaPreparada.setTimestamp(1, fechaHora);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {
                VeterinarioDTO vet = new VeterinarioDTO(
                        -1, // Assuming idVeterinario is not returned by the procedure
                        null,
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        null, // Assuming telefonoCelular is not returned
                        null, // Assuming telefonoEmergencia is not returned
                        null, // Assuming usuario is not returned
                        null, // Assuming contraseña is not returned
                        null, // Assuming calle is not returned
                        null, // Assuming numero is not returned
                        null  // Assuming colonia is not returned
                );
                veterinarios.add(vet);
            }
        } finally {
            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return veterinarios;
    }


}

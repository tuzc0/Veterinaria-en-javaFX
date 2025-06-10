package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.SecretarioDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SecretarioDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarSecretario(SecretarioDTO secretario) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO secretaria (idSecretaria, numeroINE, nombre, apellidos, telefono, usuario, contraseña, calle, numero, colonia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean secretarioInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, secretario.getIdSecretario());
            consultaPreparada.setString(2, secretario.getNumeroINE());
            consultaPreparada.setString(3, secretario.getNombre());
            consultaPreparada.setString(4, secretario.getApellidos());
            consultaPreparada.setString(5, secretario.getTelefono());
            consultaPreparada.setString(6, secretario.getUsuario());
            consultaPreparada.setString(7, secretario.getContrasena());
            consultaPreparada.setString(8, secretario.getCalle());
            consultaPreparada.setString(9, secretario.getNumero());
            consultaPreparada.setString(10, secretario.getColonia());
            consultaPreparada.executeUpdate();
            secretarioInsertado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return secretarioInsertado;
    }

    public boolean eliminarSecretarioPorId(int idSecretario) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM secretaria WHERE idSecretaria = ?";
        boolean secretarioEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idSecretario);
            consultaPreparada.executeUpdate();
            secretarioEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return secretarioEliminado;
    }

    public SecretarioDTO buscarSecretarioPorId(int idSecretario) throws SQLException, IOException {

        SecretarioDTO secretario = new SecretarioDTO(-1, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");
        String consultaSQL = "SELECT * FROM secretaria WHERE idSecretaria = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idSecretario);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                secretario = new SecretarioDTO(
                        resultadoConsulta.getInt("idSecretaria"),
                        resultadoConsulta.getString("numeroINE"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("telefono"),
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

        return secretario;
    }

    public SecretarioDTO buscarSecretarioPorUsuarioYContraseña(String usuario, String contraseña) throws SQLException, IOException {

        SecretarioDTO secretario = new SecretarioDTO(-1, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A");

        String consultaSQL = "SELECT * FROM secretaria WHERE usuario = ? AND contraseña = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setString(1, usuario);
            consultaPreparada.setString(2, contraseña);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {

                secretario = new SecretarioDTO(
                        resultadoConsulta.getInt("idSecretaria"),
                        resultadoConsulta.getString("numeroINE"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("telefono"),
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

        return secretario;
    }

    public List<SecretarioDTO> listarSecretarios() throws SQLException, IOException {

        List<SecretarioDTO> secretarios = new ArrayList<>();
        String consultaSQL = "SELECT * FROM secretaria";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                SecretarioDTO secretario = new SecretarioDTO(
                        resultadoConsulta.getInt("idSecretaria"),
                        resultadoConsulta.getString("numeroINE"),
                        resultadoConsulta.getString("nombre"),
                        resultadoConsulta.getString("apellidos"),
                        resultadoConsulta.getString("telefono"),
                        resultadoConsulta.getString("usuario"),
                        resultadoConsulta.getString("contraseña"),
                        resultadoConsulta.getString("calle"),
                        resultadoConsulta.getString("numero"),
                        resultadoConsulta.getString("colonia")
                );

                secretarios.add(secretario);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return secretarios;
    }
}
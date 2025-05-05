package logica.DAOs;

import accesoadatos.ConexionBaseDeDatos;
import logica.DTOs.TelefonoContactoDTO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefonoContactoDAO {

    private Connection conexionBaseDeDatos;
    private PreparedStatement consultaPreparada = null;
    private ResultSet resultadoConsulta;

    public boolean insertarTelefonoContacto(TelefonoContactoDTO telefonoContacto) throws SQLException, IOException {

        String consultaSQL = "INSERT INTO telefonocontacto (idDueño, telefonoCelular) VALUES (?, ?)";
        boolean telefonoInsertado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, telefonoContacto.getIdUsuario());
            consultaPreparada.setString(2, telefonoContacto.getTelefono());
            consultaPreparada.executeUpdate();
            telefonoInsertado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return telefonoInsertado;
    }

    public boolean eliminarTelefonoContactoPorId(int idUsuario) throws SQLException, IOException {

        String consultaSQL = "DELETE FROM telefonocontacto WHERE idUsuario = ?";
        boolean telefonoEliminado = false;

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idUsuario);
            consultaPreparada.executeUpdate();
            telefonoEliminado = true;

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return telefonoEliminado;
    }

    public TelefonoContactoDTO buscarTelefonoContactoPorId(int idDueño) throws SQLException, IOException {

        TelefonoContactoDTO telefonoContacto = new TelefonoContactoDTO(-1, "N/A");
        String consultaSQL = "SELECT * FROM telefonocontacto WHERE idDueño = ?";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            consultaPreparada.setInt(1, idDueño);
            resultadoConsulta = consultaPreparada.executeQuery();

            if (resultadoConsulta.next()) {
                telefonoContacto = new TelefonoContactoDTO(
                        resultadoConsulta.getInt("idDueño"),
                        resultadoConsulta.getString("telefonoCelular")
                );
            }

        } finally {
            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
            if (conexionBaseDeDatos != null) {
                conexionBaseDeDatos.close();
            }
        }

        return telefonoContacto;
    }


    public List<TelefonoContactoDTO> listarTelefonosContacto() throws SQLException, IOException {

        List<TelefonoContactoDTO> telefonosContacto = new ArrayList<>();
        String consultaSQL = "SELECT * FROM telefonocontacto";

        try {

            conexionBaseDeDatos = new ConexionBaseDeDatos().getConnection();
            consultaPreparada = conexionBaseDeDatos.prepareStatement(consultaSQL);
            resultadoConsulta = consultaPreparada.executeQuery();

            while (resultadoConsulta.next()) {

                TelefonoContactoDTO telefonoContacto = new TelefonoContactoDTO(
                        resultadoConsulta.getInt("idUsuario"),
                        resultadoConsulta.getString("telefono")
                );

                telefonosContacto.add(telefonoContacto);
            }

        } finally {

            if (consultaPreparada != null) {
                consultaPreparada.close();
            }
        }

        return telefonosContacto;
    }
}

package accesoadatos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDeDatos {

    private Connection conexion;

    public ConexionBaseDeDatos() throws IOException, SQLException {

        LectorProperties propiedades = new LectorProperties();

        conexion = DriverManager.getConnection(
                propiedades.getUrl(),
                propiedades.getUsuario(),
                propiedades.getContraseña()
        );
    }

    public Connection getConnection() {
        return conexion;
    }
}
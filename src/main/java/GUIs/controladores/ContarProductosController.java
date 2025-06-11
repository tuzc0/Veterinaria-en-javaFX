package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.ProcedimientosDAO;
import accesoadatos.ConexionBaseDeDatos;

public class ContarProductosController {
    @FXML private TextField nombreProductoTX;
    @FXML private Label resultadoLB;


    @FXML
    private void contarProductos() {
        try {
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            ProcedimientosDAO dao = new ProcedimientosDAO(conexionBD);
            String nombre = nombreProductoTX.getText();
            int total = dao.contarProductosPorNombre(nombre);
            resultadoLB.setText("Total: " + total);
        } catch (Exception e) {
            resultadoLB.setText("Error: " + e.getMessage());
        }
    }
}

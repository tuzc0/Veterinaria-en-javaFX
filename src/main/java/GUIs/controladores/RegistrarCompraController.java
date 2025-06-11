package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.ProcedimientosDAO;
import accesoadatos.ConexionBaseDeDatos;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RegistrarCompraController {
    @FXML private TextField idProductoTX;
    @FXML private TextField idDuenoTX;
    @FXML private DatePicker fechaPicker;
    @FXML private Label mensajeLB;



    @FXML
    private void registrarCompra() {
        try {
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            ProcedimientosDAO dao = new ProcedimientosDAO(conexionBD);
            int idProducto = Integer.parseInt(idProductoTX.getText());
            int idDueno = Integer.parseInt(idDuenoTX.getText());
            LocalDateTime fechaHora = fechaPicker.getValue().atTime(java.time.LocalTime.now());
            dao.registrarCompra(idProducto, idDueno, Timestamp.valueOf(fechaHora));
            mensajeLB.setText("Compra registrada exitosamente.");
        } catch (Exception e) {
            mensajeLB.setText("Error: " + e.getMessage());
        }
    }
}

package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.ProcedimientosDAO;
import accesoadatos.ConexionBaseDeDatos;

public class ContarCitasController {
    @FXML private TextField idVetTX;
    @FXML private TextField mesTX;
    @FXML private TextField anioTX;
    @FXML private Label totalLB;

   ;

    @FXML
    private void contarCitas() {
        try {
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            ProcedimientosDAO dao = new ProcedimientosDAO(conexionBD);
            int id = Integer.parseInt(idVetTX.getText());
            int mes = Integer.parseInt(mesTX.getText());
            int anio = Integer.parseInt(anioTX.getText());
            int total = dao.contarCitasPorMes(id, mes, anio);
            totalLB.setText("Total: " + total);
        } catch (Exception e) {
            totalLB.setText("Error: " + e.getMessage());
        }
    }
}


package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.ProcedimientosDAO;
import accesoadatos.ConexionBaseDeDatos;
import logica.DAOs.VeterinarioDAO;
import logica.DTOs.VeterinarioDTO;

public class ContarCitasController {

    @FXML private TextField mesTX;
    @FXML private TextField anioTX;
    @FXML private Label totalLB;
    @FXML private ComboBox<VeterinarioDTO> comboVeterinario;


    @FXML
    public void initialize() {
        cargarVeterinarios();
    }

    private void cargarVeterinarios() {
        try {
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            VeterinarioDAO dao = new VeterinarioDAO();
            comboVeterinario.getItems().clear();
            comboVeterinario.getItems().addAll(dao.listarVeterinarios());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al cargar veterinarios: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void contarCitas() {
        try {
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            ProcedimientosDAO dao = new ProcedimientosDAO(conexionBD);
            int id = comboVeterinario.getValue().getIdVeterinario();
            int mes = Integer.parseInt(mesTX.getText());
            int anio = Integer.parseInt(anioTX.getText());
            int total = dao.contarCitasPorMes(id, mes, anio);
            totalLB.setText("Total: " + total);
        } catch (Exception e) {
            totalLB.setText("Error: " + e.getMessage());
        }
    }
}


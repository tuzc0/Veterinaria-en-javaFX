package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logica.DAOs.ProductosPendientesDAO;
import logica.DTOs.ProductosPendientesDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorListaProductosPendientesGUI implements Initializable {

    @FXML
    private TableView<ProductosPendientesDTO> tablaProductosPendientes;
    @FXML
    private TableColumn<ProductosPendientesDTO, Integer> columnaIdProducto;
    @FXML
    private TableColumn<ProductosPendientesDTO, Timestamp> columnaFechaRegistro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnaIdProducto.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getIdProducto()).asObject());
        columnaFechaRegistro.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFechaRegistro()));

        cargarProductosPendientes();
    }

    private void cargarProductosPendientes() {
        try {
            ProductosPendientesDAO dao = new ProductosPendientesDAO();
            List<ProductosPendientesDTO> lista = dao.listarProductosPendientes();
            tablaProductosPendientes.getItems().setAll(lista);
        } catch (Exception e) {
            mostrarAlertaError("No se pudieron cargar los productos pendientes:\n" + e.getMessage());
        }
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error al cargar productos pendientes");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import logica.ContenedorProductosPendientes;
import logica.DAOs.ProductoDAO;
import logica.DAOs.ProductosPendientesDAO;
import logica.DTOs.ProductoDTO;
import logica.DTOs.ProductosPendientesDTO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorListaProductosPendientesGUI implements Initializable {

    @FXML
    private TableView<ContenedorProductosPendientes> tablaProductosPendientes;
    @FXML
    private TableColumn<ContenedorProductosPendientes, Integer> columnaIdProducto;
    @FXML
    private TableColumn<ContenedorProductosPendientes, Timestamp> columnaFechaRegistro;

    @FXML
    private TableColumn<ContenedorProductosPendientes, String> columnaNombreProducto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        cargarProductosPendientes();
    }

    private void cargarProductosPendientes() {
        try {
            ProductosPendientesDAO dao = new ProductosPendientesDAO();
            ProductoDAO productoDAO = new ProductoDAO();
            List<ProductosPendientesDTO> listaPendientes = dao.listarProductosPendientes();
            List<ProductoDTO> productos = productoDAO.listarProductos();

            // Create a list of ContenedorProductosPendientes
            List<ContenedorProductosPendientes> contenedores = new ArrayList<>();

            for (ProductosPendientesDTO pendiente : listaPendientes) {
                for (ProductoDTO producto : productos) {
                    if (pendiente.getIdProducto() == producto.getIdProducto()) {
                        contenedores.add(new ContenedorProductosPendientes(producto, pendiente));
                        break;
                    }
                }
            }

            tablaProductosPendientes.getItems().setAll(contenedores);

            columnaIdProducto.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getProductoPendiente().getIdProducto()).asObject());
            columnaNombreProducto.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().getProductoPendiente().getNombre()));
            columnaFechaRegistro.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().getProductosPendientesDTO().getFechaRegistro()));

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
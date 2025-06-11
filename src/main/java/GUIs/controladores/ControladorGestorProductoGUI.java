package GUIs.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.ProductoDAO;
import logica.DTOs.ProductoDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControladorGestorProductoGUI {

    @FXML private TextField campoBusquedaProducto;
    @FXML private TableView<ProductoDTO> tablaProductos;
    @FXML private TableColumn<ProductoDTO, Integer> columnaId;
    @FXML private TableColumn<ProductoDTO, String> columnaNombre;
    @FXML private TableColumn<ProductoDTO, Double> columnaPrecio;
    @FXML private TableColumn<ProductoDTO, String> columnaEspecie;
    @FXML private TableColumn<ProductoDTO, String> columnaTipo;
    @FXML private TableColumn<ProductoDTO, String> columnaMarca;
    @FXML private TableColumn<ProductoDTO, Integer> columnaExistencia;

    @FXML private Label campoIdEncontrado, campoNombreEncontrado, campoPrecioEncontrado, campoEspecieEncontrada,
            campoTipoEncontrado, campoMarcaEncontrada, campoExistenciaEncontrada, campoNumeroProductosSeleccionados;

    @FXML private TextField campoIdEditable, campoNombreEditable, campoPrecioEditable, campoEspecieEditable,
            campoTipoEditable, campoMarcaEditable, campoExistenciaEditable;

    @FXML private Button botonGuardar, botonCancelar, botonEliminarProducto, botonEditarProducto, botonRegistrarProducto;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private ObservableList<ProductoDTO> productos = FXCollections.observableArrayList();
    private ProductoDTO productoSeleccionado;

    @FXML
    public void initialize() {
        columnaId.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getIdProducto()).asObject());
        columnaNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        columnaPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrecio()).asObject());
        columnaEspecie.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEspecie()));
        columnaTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));
        columnaMarca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMarca()));
        columnaExistencia.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getExistencia()).asObject());

        tablaProductos.setItems(productos);
        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> mostrarDetallesProducto(newSel));
        cargarProductos();
    }

    private void cargarProductos() {
        try {
            List<ProductoDTO> lista = productoDAO.listarProductos();
            productos.setAll(lista);
            campoNumeroProductosSeleccionados.setText("Total productos: " + lista.size());
        } catch (SQLException | IOException e) {
            mostrarAlerta("Error al cargar productos: " + e.getMessage());
        }
    }

    @FXML
    private void buscarProducto() {
        String texto = campoBusquedaProducto.getText().trim().toLowerCase();
        if (texto.isEmpty()) {
            cargarProductos();
            return;
        }
        try {
            List<ProductoDTO> lista = productoDAO.listarProductos();
            List<ProductoDTO> filtrados = lista.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(texto) ||
                            p.getMarca().toLowerCase().contains(texto))
                    .toList();
            productos.setAll(filtrados);
            campoNumeroProductosSeleccionados.setText("Total productos: " + filtrados.size());
        } catch (Exception e) {
            mostrarAlerta("Error al buscar productos: " + e.getMessage());
        }
    }

    private void mostrarDetallesProducto(ProductoDTO producto) {
        productoSeleccionado = producto;
        if (producto == null) {
            campoIdEncontrado.setText("");
            campoNombreEncontrado.setText("");
            campoPrecioEncontrado.setText("");
            campoEspecieEncontrada.setText("");
            campoTipoEncontrado.setText("");
            campoMarcaEncontrada.setText("");
            campoExistenciaEncontrada.setText("");
            return;
        }
        campoIdEncontrado.setText(String.valueOf(producto.getIdProducto()));
        campoNombreEncontrado.setText(producto.getNombre());
        campoPrecioEncontrado.setText(String.valueOf(producto.getPrecio()));
        campoEspecieEncontrada.setText(producto.getEspecie());
        campoTipoEncontrado.setText(producto.getTipo());
        campoMarcaEncontrada.setText(producto.getMarca());
        campoExistenciaEncontrada.setText(String.valueOf(producto.getExistencia()));
    }

    private void setEdicionVisible(boolean visible) {

        campoIdEditable.setVisible(visible);
        campoNombreEditable.setVisible(visible);
        campoPrecioEditable.setVisible(visible);
        campoEspecieEditable.setVisible(visible);
        campoTipoEditable.setVisible(visible);
        campoMarcaEditable.setVisible(visible);
        campoExistenciaEditable.setVisible(visible);

        campoIdEncontrado.setVisible(!visible);
        campoNombreEncontrado.setVisible(!visible);
        campoPrecioEncontrado.setVisible(!visible);
        campoEspecieEncontrada.setVisible(!visible);
        campoTipoEncontrado.setVisible(!visible);
        campoMarcaEncontrada.setVisible(!visible);
        campoExistenciaEncontrada.setVisible(!visible);

        botonGuardar.setVisible(visible);
        botonCancelar.setVisible(visible);

        botonEliminarProducto.setVisible(!visible);
        botonEditarProducto.setVisible(!visible);
        botonRegistrarProducto.setVisible(!visible);

    }

    @FXML
    private void editarProducto() {

        if (productoSeleccionado == null) return;
        campoIdEditable.setText(String.valueOf(productoSeleccionado.getIdProducto()));
        campoNombreEditable.setText(productoSeleccionado.getNombre());
        campoPrecioEditable.setText(String.valueOf(productoSeleccionado.getPrecio()));
        campoEspecieEditable.setText(productoSeleccionado.getEspecie());
        campoTipoEditable.setText(productoSeleccionado.getTipo());
        campoMarcaEditable.setText(productoSeleccionado.getMarca());
        campoExistenciaEditable.setText(String.valueOf(productoSeleccionado.getExistencia()));

        setEdicionVisible(true);
    }

        @FXML
    private void guardarCambiosProducto() {
        try {
            ProductoDTO modificado = new ProductoDTO(
                    Integer.parseInt(campoIdEditable.getText()),
                    campoNombreEditable.getText(),
                    Double.parseDouble(campoPrecioEditable.getText()),
                    campoEspecieEditable.getText(),
                    campoTipoEditable.getText(),
                    campoMarcaEditable.getText(),
                    Integer.parseInt(campoExistenciaEditable.getText())
            );
            productoDAO.modificarProducto(modificado);
            cargarProductos();
            cancelarEdicionProducto();
        } catch (Exception e) {
            mostrarAlerta("Error al guardar cambios: " + e.getMessage());
        }
    }

    @FXML
    private void cancelarEdicionProducto() {
        setEdicionVisible(false);
    }

    @FXML
    private void eliminarProducto() {
        if (productoSeleccionado == null) return;
        try {
            productoDAO.eliminarProductoPorId(productoSeleccionado.getIdProducto());
            cargarProductos();
        } catch (Exception e) {
            mostrarAlerta("Error al eliminar producto: " + e.getMessage());
        }
    }

    @FXML
    private void abrirVentanaRegistrarProducto() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/InsertarProducto.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("Registrar Producto");
            stage.setScene(new javafx.scene.Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

            cargarProductos();
        } catch (Exception e) {
            mostrarAlerta("No se pudo abrir la ventana de registro: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.showAndWait();
    }
}
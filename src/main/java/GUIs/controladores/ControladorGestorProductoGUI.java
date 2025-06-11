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

    @FXML private Button botonGuardar, botonCancelar;

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
        productos.setAll(productos.filtered(p ->
                p.getNombre().toLowerCase().contains(texto) ||
                        p.getMarca().toLowerCase().contains(texto)
        ));
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

        campoIdEditable.setVisible(true);
        campoNombreEditable.setVisible(true);
        campoPrecioEditable.setVisible(true);
        campoEspecieEditable.setVisible(true);
        campoTipoEditable.setVisible(true);
        campoMarcaEditable.setVisible(true);
        campoExistenciaEditable.setVisible(true);

        botonGuardar.setVisible(true);
        botonCancelar.setVisible(true);
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
        campoIdEditable.setVisible(false);
        campoNombreEditable.setVisible(false);
        campoPrecioEditable.setVisible(false);
        campoEspecieEditable.setVisible(false);
        campoTipoEditable.setVisible(false);
        campoMarcaEditable.setVisible(false);
        campoExistenciaEditable.setVisible(false);
        botonGuardar.setVisible(false);
        botonCancelar.setVisible(false);
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
        // Implementar ventana de registro si es necesario
        mostrarAlerta("Funcionalidad de registro no implementada.");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.showAndWait();
    }
}
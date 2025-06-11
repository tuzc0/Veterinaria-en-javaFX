package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logica.DAOs.ProductoDAO;
import logica.DTOs.ProductoDTO;

public class ControladorInsertarProductoGUI {

    @FXML private TextField campoNombre;
    @FXML private TextField campoPrecio;
    @FXML private TextField campoEspecie;
    @FXML private TextField campoTipo;
    @FXML private TextField campoMarca;
    @FXML private TextField campoExistencia;
    @FXML private Label etiquetaMensaje;

    private final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private void initialize() {
        campoPrecio.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*(\\.\\d{0,2})?")) {
                campoPrecio.setText(oldVal);
            }
        });
        campoExistencia.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d*")) {
                campoExistencia.setText(oldVal);
            }
        });
    }

    @FXML
    private void guardarProducto() {
        String nombre = campoNombre.getText().trim();
        String precioStr = campoPrecio.getText().trim();
        String especie = campoEspecie.getText().trim();
        String tipo = campoTipo.getText().trim();
        String marca = campoMarca.getText().trim();
        String existenciaStr = campoExistencia.getText().trim();

        if (nombre.isEmpty() || precioStr.isEmpty() || especie.isEmpty() ||
                tipo.isEmpty() || marca.isEmpty() || existenciaStr.isEmpty()) {
            etiquetaMensaje.setText("Todos los campos son obligatorios.");
            return;
        }

        try {
            if (!precioStr.matches("\\d+(\\.\\d{1,2})?")) {
                etiquetaMensaje.setText("Precio inválido. Ejemplo válido: 12.50");
                return;
            }
            if (!existenciaStr.matches("\\d+")) {
                etiquetaMensaje.setText("Existencia debe ser un número entero.");
                return;
            }

            double precio = Double.parseDouble(precioStr);
            int existencia = Integer.parseInt(existenciaStr);

            ProductoDTO producto = new ProductoDTO(0, nombre, precio, especie, tipo, marca, existencia);
            boolean exito = productoDAO.insertarProducto(producto);

            if (exito) {
                etiquetaMensaje.setText("Producto registrado correctamente.");
                cerrarVentana();
            } else {
                etiquetaMensaje.setText("No se pudo registrar el producto.");
            }
        } catch (Exception e) {
            etiquetaMensaje.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void cancelarRegistro() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) campoNombre.getScene().getWindow();
        stage.close();
    }
}
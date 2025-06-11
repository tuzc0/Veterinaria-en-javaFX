package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logica.DAOs.ProductoDAO;
import javafx.scene.control.TextFormatter;
import logica.DTOs.ProductoDTO;

import java.sql.SQLException;

public class ControladorInsertarProductoGUI {

    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoMarca;
    @FXML
    private Spinner<Double> spinnerPrecio;
    @FXML
    private ComboBox<String> comboEspecie;
    @FXML
    private ComboBox<String> comboTipo;
    @FXML
    private Spinner<Integer> spinnerExistencia;
    @FXML
    private Label etiquetaMensaje;

    private final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private void initialize() {

        cargarDatos();

    }

    public void cargarDatos() {

        spinnerPrecio.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, Double.MAX_VALUE, 0.0, 0.01));
        spinnerPrecio.getValueFactory().setConverter(new javafx.util.converter.DoubleStringConverter());
        spinnerPrecio.setEditable(true);


        spinnerPrecio.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("-?\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        }));


        spinnerExistencia.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0));
        spinnerExistencia.setEditable(true);


        spinnerExistencia.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }
            return null;
        }));


        comboEspecie.getItems().addAll("Perro", "Gato");
        comboTipo.getItems().addAll("Alimento", "Accesorio", "Medicamento", "Juguete", "Higiene", "Otros");
    }

    @FXML
    private void guardarProducto() {
        String nombre = campoNombre.getText().trim();
        float precio = spinnerPrecio.getValue().floatValue();
        String especie = comboEspecie.getValue();
        String tipo = comboTipo.getValue();
        String marca = campoMarca.getText().trim();
        int existencia = spinnerExistencia.getValue();

        if (nombre.isEmpty() || especie == null || tipo == null || marca.isEmpty()) {
            etiquetaMensaje.setText("Por favor, complete todos los campos obligatorios.");
            return;
        }

        try {

            ProductoDTO producto = new ProductoDTO(0, nombre, precio, especie, tipo, marca, existencia);
            boolean exito = productoDAO.insertarProducto(producto);

            if (exito) {
                etiquetaMensaje.setText("Producto registrado correctamente.");
                cerrarVentana();
            } else {
                etiquetaMensaje.setText("No se pudo registrar el producto.");
            }
        } catch (SQLException e) {

            etiquetaMensaje.setText("Error al registrar el producto: " + e.getMessage());
        } catch (Exception e) {

            etiquetaMensaje.setText("Error inesperado: " + e.getMessage());
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

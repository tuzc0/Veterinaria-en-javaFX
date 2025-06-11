package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.DueñoDAO;
import logica.DAOs.ProcedimientosDAO;
import accesoadatos.ConexionBaseDeDatos;
import logica.DAOs.ProductoDAO;
import logica.DTOs.DueñoDTO;
import logica.DTOs.ProductoDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RegistrarCompraController {

    @FXML private Label lbFecha;
    @FXML private Label mensajeLB;
    @FXML private ComboBox<ProductoDTO> comboProducto;
    @FXML private ComboBox<DueñoDTO> comboDueño;
    @FXML private Spinner<Integer> spinnerCantidad;

    Utilidades utilidades = new Utilidades();


    @FXML
    public void initialize() {
        cargarProductos();
        cargarDueños();
        inicializarSpinner();
        lbFecha.setText(""+LocalDateTime.now().toLocalDate());
    }

    public void inicializarSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spinnerCantidad.setValueFactory(valueFactory);
        spinnerCantidad.setEditable(true);
        spinnerCantidad.getEditor().setText("1");
    }



    @FXML
    private void registrarCompra() {
        try {

            ProductoDAO productoDAO = new ProductoDAO();

            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            ProcedimientosDAO dao = new ProcedimientosDAO(conexionBD);
            int idProducto = comboProducto.getSelectionModel().getSelectedItem().getIdProducto();
            int idDueno = comboDueño.getSelectionModel().getSelectedItem().getIdDueño();
            int cantidad = spinnerCantidad.getValue();

            int existencia = productoDAO.validarDisponibilidadProducto(idProducto);

            if (existencia <= 0) {
                utilidades.mostrarAlerta("Producto no disponible", "El producto con ID " + idProducto + " no está disponible para compra.","Error");
                return;
            }

            LocalDateTime fechaHora = LocalDateTime.now();
            dao.registrarCompra(idProducto, idDueno, Timestamp.valueOf(fechaHora), cantidad);
            mensajeLB.setText("Compra registrada exitosamente.");
            utilidades.mostrarAlerta("Compra registrada", "La compra del producto con ID " + idProducto + " ha sido registrada exitosamente.","Información");

        } catch (Exception e) {
            mensajeLB.setText("Error: " + e.getMessage());
        }
    }


    public void cargarProductos() {
        try {
            ProductoDAO productoDAO = new ProductoDAO();
            comboProducto.getItems().clear();
            comboProducto.getItems().addAll(productoDAO.listarProductos());
        } catch (Exception e) {
            utilidades.mostrarAlerta("Error al cargar productos", "No se pudieron cargar los productos: " + e.getMessage(), "Error");
        }
    }


    public void cargarDueños() {
        try {
            DueñoDAO dueñoDAO = new DueñoDAO();
            comboDueño.getItems().clear();
            comboDueño.getItems().addAll(dueñoDAO.listarDueños());
        } catch (Exception e) {
            utilidades.mostrarAlerta("Error al cargar dueños", "No se pudieron cargar los dueños: " + e.getMessage(), "Error");
        }
    }
}

package GUIs.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.DueñoDAO;
import logica.DAOs.TelefonoContactoDAO;
import logica.DTOs.DueñoDTO;
import logica.DTOs.TelefonoContactoDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControladorGestorDueñoGUI {


    @FXML
    private TextField campoTelefonoBuscado;

    @FXML
    private Label campoNombreEncontrado;

    @FXML
    private Label campoApellidosEncontrados;

    @FXML
    private Label campoCalleEncontrado;

    @FXML
    private Label campoNumeroEncontrado;

    @FXML
    private Label campoColoniaEncontrada;

    @FXML
    private Label campoTelefonoEncontrado;

    @FXML
    private TextField campoNombreEditable;

    @FXML
    private TextField campoApellidoEditable;

    @FXML
    private TextField campoCalleEditable;

    @FXML
    private TextField campoNumeroEditable;

    @FXML
    private TextField campoColoniaEditable;

    @FXML
    private Button botonGuardar;

    @FXML
    private Button botonCancelar;

    @FXML
    private Button botonEliminarSeleccionado;

    @FXML
    private Button botonEliminarDueño;

    @FXML
    private Button botonEditarDueño;

    @FXML
    private Button botonRegistrarDueño;

    @FXML private TableView<DueñoDTO> tablaAnimales;
    @FXML private TableColumn<DueñoDTO, String> columnaNombre;
    @FXML private TableColumn<DueñoDTO, String> columnaApellidos;
    @FXML private TableColumn<DueñoDTO, String> columnaCalle;
    @FXML private TableColumn<DueñoDTO, String> columnaNumero;
    @FXML private TableColumn<DueñoDTO, String> columnaColonia;

    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaApellidos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellidos()));
        columnaCalle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCalle()));
        columnaNumero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumero()));
        columnaColonia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColonia()));


        tablaAnimales.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            mostrarDetallesDesdeTabla(newSel);
            botonEliminarSeleccionado.setDisable(newSel == null);
        });

        cargarDueños();
        ;
    }

    private void cargarDueños() {
        try {
            List<DueñoDTO> listaDueños = new DueñoDAO().listarDueños();
            tablaAnimales.getItems().setAll(listaDueños);
        } catch (SQLException | IOException e) {
            e.printStackTrace();

        }
    }

    private void mostrarDetallesDesdeTabla(DueñoDTO dueño) {

        if (dueño != null) {
            campoNombreEncontrado.setText(dueño.getNombre());
            campoApellidosEncontrados.setText(dueño.getApellidos());
            campoCalleEncontrado.setText(dueño.getCalle());
            campoNumeroEncontrado.setText(dueño.getNumero());
            campoColoniaEncontrada.setText(dueño.getColonia());
            int idDueño = dueño.getIdDueño();

            try {

                TelefonoContactoDAO telefonoDAO = new TelefonoContactoDAO();
                String telefono = telefonoDAO.buscarTelefonoContactoPorId(idDueño).getTelefono();
                campoTelefonoEncontrado.setText(telefono);

            } catch (SQLException | IOException e) {
                campoTelefonoEncontrado.setText("Error");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void buscarDueñoPorTelefono() {
        String telefono = campoTelefonoBuscado.getText().trim();

        if (telefono.isEmpty()) {
            mostrarAlerta("Error", "Campo vacío", "Por favor, ingrese un número de teléfono.");
            return;
        }

        try {
            DueñoDAO dueñoDAO = new DueñoDAO();
            DueñoDTO dueñoEncontrado = dueñoDAO.buscarDueñoPorTelefono(telefono);

            if (dueñoEncontrado != null) {
                mostrarDetallesDesdeTabla(dueñoEncontrado);
            } else {
                mostrarAlerta("Sin resultados", "No se encontró dueño", "No se encontró ningún dueño con ese número de teléfono.");
            }

        } catch (SQLException | IOException e) {
            mostrarAlerta("Error", "Error de base de datos", "Ocurrió un error al buscar el dueño.");
            e.printStackTrace();
        }
    }

    @FXML
    private void editarDueño() {

        campoNombreEditable.setText(campoNombreEncontrado.getText());
        campoApellidoEditable.setText(campoApellidosEncontrados.getText());
        campoCalleEditable.setText(campoCalleEncontrado.getText());
        campoNumeroEditable.setText(campoNumeroEncontrado.getText());
        campoColoniaEditable.setText(campoColoniaEncontrada.getText());

        botonCancelar.setVisible(true);
        botonGuardar.setVisible(true);
        botonEliminarDueño.setVisible(false);
        botonRegistrarDueño.setDisable(true);
        botonEditarDueño.setVisible(false);

        tablaAnimales.setDisable(true);

    }

    @FXML
    private void guardarCambios(){

    }

    @FXML
    private void cancelarEdicion(){

        campoNombreEditable.clear();
        campoApellidoEditable.clear();
        campoCalleEditable.clear();
        campoNumeroEditable.clear();
        campoColoniaEditable.clear();

        botonCancelar.setVisible(false);
        botonGuardar.setVisible(false);

        botonEliminarDueño.setVisible(true);
        botonRegistrarDueño.setDisable(false);
        botonRegistrarDueño.setVisible(true);

        tablaAnimales.setDisable(false);

    }


    private void mostrarAlerta(String titulo, String cabecera, String contenido) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }













}

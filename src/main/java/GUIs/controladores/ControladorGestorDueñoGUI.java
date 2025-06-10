package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    Utilidades utilidades = new Utilidades();

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

        try {
            DueñoDAO dueñoDAO = new DueñoDAO();

            if (telefono.isEmpty()) {
                cargarDueños();
            } else {
                DueñoDTO dueñoEncontrado = dueñoDAO.buscarDueñoPorTelefono(telefono);

                if (dueñoEncontrado != null) {

                    tablaAnimales.getItems().setAll(dueñoEncontrado);
                } else {
                    mostrarAlerta("Sin resultados", "No se encontró dueño", "No se encontró ningún dueño con ese número de teléfono.");
                    tablaAnimales.getItems().clear();
                }
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

        campoNombreEncontrado.setVisible(false);
        campoApellidosEncontrados.setVisible(false);
        campoCalleEncontrado.setVisible(false);
        campoNumeroEncontrado.setVisible(false);
        campoColoniaEncontrada.setVisible(false);

        campoNombreEditable.setVisible(true);
        campoApellidoEditable.setVisible(true);
        campoCalleEditable.setVisible(true);
        campoNumeroEditable.setVisible(true);
        campoColoniaEditable.setVisible(true);

        botonCancelar.setVisible(true);
        botonGuardar.setVisible(true);
        botonEliminarDueño.setVisible(false);
        botonRegistrarDueño.setDisable(true);
        botonEditarDueño.setVisible(false);

        tablaAnimales.setDisable(true);

    }

    @FXML
    private void guardarCambios(){

        String nombre = campoNombreEditable.getText();
        String apellidos = campoApellidoEditable.getText();
        String calle = campoCalleEditable.getText();
        String numero = campoNumeroEditable.getText();
        String colonia = campoColoniaEditable.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || calle.isEmpty() || numero.isEmpty() || colonia.isEmpty()) {
            mostrarAlerta("Error", "Campos vacíos", "Por favor, complete todos los campos.");
            return;
        }

        DueñoDTO dueñoSeleccionado = tablaAnimales.getSelectionModel().getSelectedItem();

        if (dueñoSeleccionado != null) {
            dueñoSeleccionado.setNombre(nombre);
            dueñoSeleccionado.setApellidos(apellidos);
            dueñoSeleccionado.setCalle(calle);
            dueñoSeleccionado.setNumero(numero);
            dueñoSeleccionado.setColonia(colonia);

            try {

                DueñoDAO dueñoDAO = new DueñoDAO();
                dueñoDAO.modificarDueño(dueñoSeleccionado);
                cargarDueños();
                mostrarAlerta("Éxito", "Dueño actualizado", "Los datos del dueño se han actualizado correctamente.");
            } catch (SQLException | IOException e) {
                mostrarAlerta("Error", "Error de base de datos", "Ocurrió un error al actualizar el dueño.");
                e.printStackTrace();
            }
        }

        campoNombreEncontrado.setText(nombre);
        campoApellidosEncontrados.setText(apellidos);
        campoCalleEncontrado.setText(calle);
        campoNumeroEncontrado.setText(numero);
        campoColoniaEncontrada.setText(colonia);

        cargarDueños();
        cancelarEdicion();


    }

    @FXML
    private void cancelarEdicion(){

        campoNombreEditable.setVisible(false);
        campoApellidoEditable.setVisible(false);
        campoCalleEditable.setVisible(false);
        campoNumeroEditable.setVisible(false);
        campoColoniaEditable.setVisible(false);

        campoTelefonoEncontrado.setVisible(true);
        campoNombreEncontrado.setVisible(true);
        campoApellidosEncontrados.setVisible(true);
        campoCalleEncontrado.setVisible(true);
        campoNumeroEncontrado.setVisible(true);
        campoColoniaEncontrada.setVisible(true);



        botonCancelar.setVisible(false);
        botonGuardar.setVisible(false);

        botonEliminarDueño.setVisible(true);
        botonRegistrarDueño.setDisable(false);
        botonRegistrarDueño.setVisible(true);
        botonEditarDueño.setVisible(true);

        tablaAnimales.setDisable(false);

    }

    @FXML
    private void eliminarDueño() {

        utilidades.mostrarAlertaConfirmacion(
                "Confirmar eliminación",
                "¿Está seguro que desea eliminar este dueño?",
                "Se borrara . Esta acción no se puede deshacer.",
                () -> {

                    borrarDueño();
                },
                () -> {
                    utilidades.mostrarAlerta("Cancelado",
                            "Eliminación cancelada",
                            "No se ha eliminado ningun registro.");
                }

        );

    }

    public void borrarDueño() {

        DueñoDTO dueñoSeleccionado = tablaAnimales.getSelectionModel().getSelectedItem();
        String telefonoBuscado = campoTelefonoBuscado.getText().trim();

        try {
            DueñoDAO dueñoDAO = new DueñoDAO();
            boolean eliminado = false;

            if (!telefonoBuscado.isEmpty()) {

                DueñoDTO dueñoEncontrado = dueñoDAO.buscarDueñoPorTelefono(telefonoBuscado);
                if (dueñoEncontrado != null) {
                    eliminado = dueñoDAO.eliminarDueñoPorId(dueñoEncontrado.getIdDueño());
                } else {
                    mostrarAlerta("Error", "No se encontró dueño", "No se encontró ningún dueño con ese número de teléfono.");
                    return;
                }
            } else if (dueñoSeleccionado != null) {

                eliminado = dueñoDAO.eliminarDueñoPorId(dueñoSeleccionado.getIdDueño());
            } else {
                mostrarAlerta("Error", "Ningún dueño seleccionado", "Por favor, seleccione un dueño para eliminar o ingrese un número de teléfono.");
                return;
            }

            if (eliminado) {
                mostrarAlerta("Éxito", "Dueño eliminado", "El dueño ha sido eliminado correctamente.");
                cargarDueños();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar", "No se pudo eliminar el dueño. Intente nuevamente.");
            }
        } catch (SQLException | IOException e) {
            mostrarAlerta("Error", "Error de base de datos", "Ocurrió un error al eliminar el dueño.");
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirVentanaRegistrarDueño() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/NuevoDueñoGUI.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Registrar Dueño");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            cargarDueños();

        } catch (IOException e) {
            mostrarAlerta("Error", "Error al abrir la ventana", "No se pudo abrir la ventana de registro.");
            e.printStackTrace();
        }
    }


    private void mostrarAlerta(String titulo, String cabecera, String contenido) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }


}

package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logica.DAOs.DueñoDAO;
import logica.DAOs.TelefonoContactoDAO;
import logica.DTOs.DueñoDTO;
import logica.DTOs.TelefonoContactoDTO;
import logica.Validaciones;

import java.io.IOException;
import java.sql.SQLException;

public class ControladorNuevoDuenoGUI {

    @FXML
    private ImageView imagen;

    @FXML
    private TextField campoNombre;

    @FXML
    private TextField campoApellidos;

    @FXML
    private TextField campoCalle;

    @FXML
    private TextField campoNumero;

    @FXML
    private TextField campoColonia;

    @FXML
    private TextField campoTelefono;

    Utilidades utilidades = new Utilidades();

    @FXML
    public void initialize() {

        try {

            imagen.setImage(new Image("https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png"));

        } catch (Exception e) {

            utilidades.mostrarAlerta("Error", "Error de conexión", "Ocurrio un error al cargar parte de la interfaz. ");
        }
    }


    @FXML
    private void registrarDueno() {

        String nombre = campoNombre.getText();
        String apellidos = campoApellidos.getText();
        String calle = campoCalle.getText();
        String numero = campoNumero.getText();
        String colonia = campoColonia.getText();
        String telefono = campoTelefono.getText();

        if (!validarCampos()) {
            return;
        }

        int idDueño = 0;
        DueñoDAO dueñoDAO = new DueñoDAO();

        try {
            DueñoDTO dueñoExistente = dueñoDAO.buscarDueñoPorTelefono(telefono);

            // Check if the owner already exists
            if (dueñoExistente != null && dueñoExistente.getIdDueño() != -1) {
                utilidades.mostrarAlerta("Error", "Dueño ya registrado", "El dueño ya se encuentra registrado.");
                return;
            }

            DueñoDTO dueñoDTO = new DueñoDTO(0, nombre, apellidos, calle, numero, colonia);
            TelefonoContactoDAO telefonoDAO = new TelefonoContactoDAO();

            idDueño = dueñoDAO.insertarDueño(dueñoDTO);
            TelefonoContactoDTO telefonoDTO = new TelefonoContactoDTO(idDueño, telefono);
            telefonoDAO.insertarTelefonoContacto(telefonoDTO);

        } catch (SQLException e) {
            utilidades.mostrarAlerta("Error", "Error de base de datos", "Ocurrió un error al insertar al dueño.");
            e.printStackTrace();
        } catch (IOException e) {
            utilidades.mostrarAlerta("Error", "Error de entrada/salida", "Ocurrió un error al insertar al dueño.");
            e.printStackTrace();
        } catch (Exception e) {
            utilidades.mostrarAlerta("Error", "Error de conexión", "Ocurrió un error al insertar al dueño.");
            e.printStackTrace();
        }

        utilidades.mostrarAlerta("Registro exitoso", "Dueño registrado", "El dueño ha sido registrado exitosamente.");
        Stage stage = (Stage) campoNombre.getScene().getWindow();
        stage.close();
    }

    public boolean validarCampos() {

        boolean camposValidos = true;

        Validaciones validaciones = new Validaciones();

        String nombre = campoNombre.getText();
        String apellidos = campoApellidos.getText();
        String calle = campoCalle.getText();
        String numero = campoNumero.getText();
        String colonia = campoColonia.getText();
        String telefono = campoTelefono.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || calle.isEmpty() || numero.isEmpty() || colonia.isEmpty() || telefono.isEmpty()) {
            utilidades.mostrarAlerta("Error", "Campos vacíos", "Por favor, complete todos los campos.");
            camposValidos = false;
        }

        if (!validaciones.validarSoloAlfabeticos(nombre)) {
            utilidades.mostrarAlerta("Error", "Nombre inválido", "El nombre contiene caracteres no permitidos.");
            camposValidos = false;
        }

        if (!validaciones.validarSoloAlfabeticos(apellidos)) {
            utilidades.mostrarAlerta("Error", "Apellidos inválidos", "Los apellidos contienen caracteres no permitidos.");
            camposValidos = false;
        }


        if (!validaciones.validarSoloAlfabeticos(colonia)) {
            utilidades.mostrarAlerta("Error", "Colonia invalida", "La colonia tiene caracteres no permitidos.");
            camposValidos = false;
        }

        if (!validaciones.validarSoloNumeros(numero)) {
            utilidades.mostrarAlerta("Error", "Número inválido", "El número debe ser un valor numérico.");
            camposValidos = false;
        }

        if (!validaciones.validarNumeroTelefono(telefono)) {
            utilidades.mostrarAlerta("Error", "Teléfono inválido", "El teléfono debe ser un valor numérico.");
            camposValidos = false;
        }

        return camposValidos;

    }

    @FXML
    private void cancelarRegistro() {


        utilidades.mostrarAlertaConfirmacion(
                "Confirmar eliminación",
                "¿Está seguro que desea cancelar el registro?",
                "Se cancelara el registro . Esta acción no se puede deshacer.",
                () -> {

                    campoNombre.clear();
                    campoApellidos.clear();
                    campoCalle.clear();
                    campoNumero.clear();
                    campoColonia.clear();
                    campoTelefono.clear();
                },
                () -> {
                    utilidades.mostrarAlerta("Cancelado",
                            "Eliminación cancelada",
                            "No se ha cancelado ningun registro.");
                }

        );


    }


}


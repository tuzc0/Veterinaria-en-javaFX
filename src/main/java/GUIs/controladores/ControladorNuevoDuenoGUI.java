package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import logica.DAOs.DueñoDAO;
import logica.DAOs.TelefonoContactoDAO;
import logica.DTOs.DueñoDTO;
import logica.DTOs.TelefonoContactoDTO;

public class ControladorNuevoDuenoGUI {

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


    @FXML
    private void registrarDueno() {

        String nombre = campoNombre.getText();
        String apellidos = campoApellidos.getText();
        String calle = campoCalle.getText();
        String numero = campoNumero.getText();
        String colonia = campoColonia.getText();
        String telefono = campoTelefono.getText();


        if (nombre.isEmpty() || apellidos.isEmpty() || calle.isEmpty() || numero.isEmpty() || colonia.isEmpty() || telefono.isEmpty()) {
            mostrarAlerta("Error", "Campos vacíos", "Por favor, complete todos los campos.");
            return;
        }

        int idDueño = 0;

        DueñoDAO dueñoDAO = new DueñoDAO();
        DueñoDTO dueñoDTO = new DueñoDTO(0, nombre, apellidos, calle, numero, colonia);

        TelefonoContactoDAO telefonoDAO = new TelefonoContactoDAO();

        try {

            idDueño = dueñoDAO.insertarDueño(dueñoDTO);
            TelefonoContactoDTO telefonoDTO = new TelefonoContactoDTO(idDueño, telefono);
            telefonoDAO.insertarTelefonoContacto(telefonoDTO);


        } catch (Exception e) {

            mostrarAlerta("Error", "Error de conexión", "Ocurrió un error al insertar al dueño.");
            e.printStackTrace();

        }

        mostrarAlerta("Registro exitoso", "Dueño registrado", "El dueño ha sido registrado exitosamente.");

    }

    @FXML
    private void cancelarRegistro() {

        campoNombre.clear();
        campoApellidos.clear();
        campoCalle.clear();
        campoNumero.clear();
        campoColonia.clear();
        campoTelefono.clear();

        mostrarAlerta("Registro cancelado", "Registro cancelado", "El registro del dueño ha sido cancelado.");

    }

    private void mostrarAlerta(String titulo, String cabecera, String contenido) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}


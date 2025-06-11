package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import logica.DAOs.SecretarioDAO;
import logica.DAOs.VeterinarioDAO;
import logica.DTOs.SecretarioDTO;
import logica.DTOs.VeterinarioDTO;
import logica.Validaciones;


import java.io.IOException;
import java.sql.SQLException;

public class ControladorRegistroUsuarioGUI {

    // Referencias a los nuevos contenedores
    @FXML private VBox camposSecretaria;
    @FXML private VBox camposVeterinario;

    // Campos comunes
    @FXML private ComboBox<String> comboTipoUsuario;
    @FXML private TextField usuarioTX;
    @FXML private PasswordField contrasenaTX;
    @FXML private TextField nombreTX;
    @FXML private TextField apellidosTX;

    // Campos específicos para Secretaria
    @FXML private TextField ineTX;
    @FXML private TextField telefonoSecTX;

    // Campos específicos para Veterinario
    @FXML private TextField cedulaTX;
    @FXML private TextField telCelularTX;
    @FXML private TextField telEmergenciaTX;

    // Dirección
    @FXML private TextField calleTX;
    @FXML private TextField numeroTX;
    @FXML private TextField coloniaTX;

    // Utilidades
    private Utilidades utilidades = new Utilidades();

    @FXML
    public void initialize() {
        // Configurar opciones del ComboBox
        comboTipoUsuario.getItems().addAll("Secretaria", "Veterinario");

        // Establecer valor por defecto
        comboTipoUsuario.getSelectionModel().selectFirst();

        // Configurar listener para cambio de tipo de usuario
        comboTipoUsuario.valueProperty().addListener((obs, oldVal, newVal) -> {
            cambiarTipoUsuario();
        });

        // Mostrar campos iniciales
        cambiarTipoUsuario();
    }

    @FXML
    private void cambiarTipoUsuario() {
        String tipo = comboTipoUsuario.getValue();
        boolean esSecretaria = tipo.equals("Secretaria");

        // Mostrar/ocultar contenedores completos
        camposSecretaria.setVisible(esSecretaria);
        camposSecretaria.setManaged(esSecretaria);
        camposVeterinario.setVisible(!esSecretaria);
        camposVeterinario.setManaged(!esSecretaria);
    }

    @FXML
    private void registrarUsuario() {
        String tipo = comboTipoUsuario.getValue();

        if (!validarCampos(tipo)) {
            return;
        }

        try {
            if (tipo.equals("Secretaria")) {
                registrarSecretaria();
            } else {
                registrarVeterinario();
            }

            utilidades.mostrarAlerta("Éxito", "Registro exitoso",
                    tipo + " registrado/a correctamente.");
            limpiarCampos();

        } catch (SQLException | IOException e) {
            utilidades.mostrarAlerta("Error", "Error al registrar",
                    "Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos(String tipoUsuario) {
        Validaciones validaciones = new Validaciones();

        // Validar campos comunes
        if (usuarioTX.getText().isEmpty() || contrasenaTX.getText().isEmpty() ||
                nombreTX.getText().isEmpty() || apellidosTX.getText().isEmpty()) {
            utilidades.mostrarAlerta("Error", "Campos vacíos",
                    "Todos los campos obligatorios deben estar llenos.");
            return false;
        }

        // Validar teléfono según tipo de usuario
        String telefono = tipoUsuario.equals("Secretaria") ?
                telefonoSecTX.getText() : telCelularTX.getText();

        if (!validaciones.validarNumeroTelefono(telefono)) {
            utilidades.mostrarAlerta("Error", "Teléfono inválido",
                    "Ingrese un número de teléfono válido (10 dígitos).");
            return false;
        }

        // Validar campos específicos
        if (tipoUsuario.equals("Secretaria")) {
            if (ineTX.getText().isEmpty()) {
                utilidades.mostrarAlerta("Error", "Campo vacío",
                        "El número de INE es obligatorio.");
                return false;
            }
        } else {
            if (cedulaTX.getText().isEmpty()) {
                utilidades.mostrarAlerta("Error", "Campo vacío",
                        "La cédula profesional es obligatoria.");
                return false;
            }

            // Validar teléfono de emergencia si está presente
            if (!telEmergenciaTX.getText().isEmpty() &&
                    !validaciones.validarNumeroTelefono(telEmergenciaTX.getText())) {
                utilidades.mostrarAlerta("Error", "Teléfono inválido",
                        "El teléfono de emergencia debe tener 10 dígitos.");
                return false;
            }
        }

        return true;
    }

    private void registrarSecretaria() throws SQLException, IOException {
        SecretarioDTO secretaria = new SecretarioDTO(
                0,
                ineTX.getText(),
                nombreTX.getText(),
                apellidosTX.getText(),
                telefonoSecTX.getText(),
                usuarioTX.getText(),
                contrasenaTX.getText(),
                calleTX.getText(),
                numeroTX.getText(),
                coloniaTX.getText()
        );

        SecretarioDAO dao = new SecretarioDAO();
        dao.insertarSecretario(secretaria);
    }

    private void registrarVeterinario() throws SQLException, IOException {
        VeterinarioDTO veterinario = new VeterinarioDTO(
                0,
                cedulaTX.getText(),
                nombreTX.getText(),
                apellidosTX.getText(),
                telCelularTX.getText(),
                telEmergenciaTX.getText(),
                usuarioTX.getText(),
                contrasenaTX.getText(),
                calleTX.getText(),
                numeroTX.getText(),
                coloniaTX.getText()
        );

        VeterinarioDAO dao = new VeterinarioDAO();
        dao.insertarVeterinario(veterinario);
    }

    @FXML
    private void cancelarRegistro() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        // Limpiar campos comunes
        usuarioTX.clear();
        contrasenaTX.clear();
        nombreTX.clear();
        apellidosTX.clear();

        // Limpiar campos específicos
        ineTX.clear();
        telefonoSecTX.clear();
        cedulaTX.clear();
        telCelularTX.clear();
        telEmergenciaTX.clear();

        // Limpiar dirección
        calleTX.clear();
        numeroTX.clear();
        coloniaTX.clear();

        // Restablecer selección por defecto
        comboTipoUsuario.getSelectionModel().selectFirst();
        cambiarTipoUsuario();
    }
}
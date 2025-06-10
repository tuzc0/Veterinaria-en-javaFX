package GUIs.controladores;


import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import logica.DAOs.*;
import logica.DTOs.*;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static logica.DTOs.Estatus.Pendiente;

public class ControladorRegistroCitaGUI {

    @FXML private DatePicker fechaPicker;
    @FXML private ComboBox<String> horaCombo;
    @FXML private ComboBox<String> tipoCitaCombo;
    @FXML private TextArea motivoText;
    @FXML private ComboBox<AnimalDTO> animalCombo;
    @FXML private ComboBox<VeterinarioDTO> veterinarioCombo;

    private Utilidades utilidades = new Utilidades();

    int idSecretario = ControladorLoginGUI.idSecretario;

    @FXML
    public void initialize() {
        cargarCombos();
        configurarControles();
    }

    private void cargarCombos() {
        try {
            // Cargar animales
            AnimalDAO animalDAO = new AnimalDAO();
            List<AnimalDTO> animales = animalDAO.listarAnimales();
            animalCombo.getItems().addAll(animales);

            // Cargar veterinarios
            VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
            List<VeterinarioDTO> veterinarios = veterinarioDAO.listarVeterinarios();
            veterinarioCombo.getItems().addAll(veterinarios);


        } catch (SQLException e) {
            utilidades.mostrarAlerta("Error", "Error de base de datos",
                    "No se pudieron cargar los datos: " + e.getMessage());
        } catch (Exception e) {
            utilidades.mostrarAlerta("Error", "Error inesperado",
                    "Ocurrió un error al cargar los datos: " + e.getMessage());
        }
    }

    private void configurarControles() {

        animalCombo.setCellFactory(param -> new ListCell<AnimalDTO>() {
            @Override
            protected void updateItem(AnimalDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getIdAnimal() + " (" + item.getRaza() + ")");
            }
        });

        animalCombo.setConverter(new StringConverter<AnimalDTO>() {
            @Override
            public String toString(AnimalDTO animal) {
                return animal == null ? null : animal.getIdAnimal() + " (" + animal.getRaza() + ")";
            }

            @Override
            public AnimalDTO fromString(String string) {
                return null;
            }
        });

    }

    @FXML
    private void registrarCita() {
        if (!validarCampos()) {
            return;
        }

        try {
            CitaDTO nuevaCita = new CitaDTO();

            // Obtener fecha y hora
            LocalDate fecha = fechaPicker.getValue();
            LocalTime hora = LocalTime.parse(horaCombo.getValue());
            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

            // Configurar cita
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(fechaHora);

            nuevaCita.setFecha(timestamp);
            nuevaCita.setTipo(tipoCitaCombo.getValue());
            nuevaCita.setMotivo(motivoText.getText());
            nuevaCita.setIdAnimal(animalCombo.getValue().getIdAnimal());
            nuevaCita.setIdVeterinario(veterinarioCombo.getValue().getIdVeterinario());
            nuevaCita.setIdSecretario(idSecretario);
            nuevaCita.setEstatus(Pendiente); // Asignar estatus por defecto

            // Insertar en base de datos
            CitaDAO citaDAO = new CitaDAO();
            boolean exito = citaDAO.insertarCita(nuevaCita);

            if (exito) {
                utilidades.mostrarAlerta("Éxito", "Cita registrada",
                        "La cita se ha registrado correctamente.");
                limpiarCampos();
            } else {
                utilidades.mostrarAlerta("Error", "Error al registrar",
                        "No se pudo registrar la cita.");
            }

        } catch (Exception e) {
            utilidades.mostrarAlerta("Error", "Error al registrar",
                    "Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        if (fechaPicker.getValue() == null) {
            utilidades.mostrarAlerta("Error", "Campo requerido", "Seleccione una fecha.");
            return false;
        }

        if (horaCombo.getValue() == null) {
            utilidades.mostrarAlerta("Error", "Campo requerido", "Seleccione una hora.");
            return false;
        }

        if (tipoCitaCombo.getValue() == null) {
            utilidades.mostrarAlerta("Error", "Campo requerido", "Seleccione un tipo de cita.");
            return false;
        }

        if (motivoText.getText().isEmpty()) {
            utilidades.mostrarAlerta("Error", "Campo requerido", "Ingrese el motivo de la cita.");
            return false;
        }

        if (animalCombo.getValue() == null) {
            utilidades.mostrarAlerta("Error", "Campo requerido", "Seleccione un animal.");
            return false;
        }

        if (veterinarioCombo.getValue() == null) {
            utilidades.mostrarAlerta("Error", "Campo requerido", "Seleccione un veterinario.");
            return false;
        }


        return true;
    }

    @FXML
    private void cancelarRegistro() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        fechaPicker.setValue(null);
        horaCombo.setValue(null);
        tipoCitaCombo.setValue(null);
        motivoText.clear();
        animalCombo.setValue(null);

    }
}
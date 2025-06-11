package GUIs.controladores;


import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import logica.DAOs.*;
import logica.DTOs.*;


import javax.imageio.IIOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private final List<String> horasDisponibles = Arrays.asList(
            "09:00", "10:00", "11:00", "12:00",
            "13:00", "14:00", "15:00", "16:00", "17:00"
    );


    @FXML
    public void initialize() {
        cargarCombos();
        configurarControles();

        // Listener para cuando cambia la fecha
        fechaPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarHorasDisponibles(newVal);
            }
        });
    }

    private void actualizarHorasDisponibles(LocalDate fechaSeleccionada) {
        try {
            // Obtener citas existentes para la fecha seleccionada
            CitaDAO citaDAO = new CitaDAO();
            List<CitaDTO> citasDelDia = citaDAO.obtenerCitasPorFecha(java.sql.Date.valueOf(fechaSeleccionada));

            // Filtrar horas ocupadas
            List<String> horasOcupadas = citasDelDia.stream()
                    .map(cita -> {
                        LocalTime hora = cita.getFecha().toLocalDateTime().toLocalTime();
                        return hora.format(DateTimeFormatter.ofPattern("HH:mm"));
                    })
                    .collect(Collectors.toList());

            // Filtrar horas disponibles
            List<String> horasDisponiblesHoy = horasDisponibles.stream()
                    .filter(hora -> !horasOcupadas.contains(hora))
                    .collect(Collectors.toList());

            // Actualizar ComboBox
            horaCombo.getItems().setAll(horasDisponiblesHoy);

            if (horasDisponiblesHoy.isEmpty()) {
                utilidades.mostrarAlerta("Información", "Horario completo",
                        "No hay horas disponibles para la fecha seleccionada.");
            }

        } catch (SQLException e) {
            utilidades.mostrarAlerta("Error", "Error de base de datos",
                    "No se pudieron cargar las citas: " + e.getMessage());
            e.printStackTrace();
        } catch (IIOException e){

            utilidades.mostrarAlerta("Error", "Error de entrada/salida",
                    "Ocurrió un error al cargar las citas: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            utilidades.mostrarAlerta("Error", "Error inesperado",
                    "Ocurrió un error al cargar las citas: " + e.getMessage());
            e.printStackTrace();
        }
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
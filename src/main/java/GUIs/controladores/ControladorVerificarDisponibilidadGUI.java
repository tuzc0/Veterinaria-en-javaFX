package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logica.DAOs.ProcedimientosDAO;
import logica.DAOs.VeterinarioDAO;
import logica.DTOs.VeterinarioDTO;
import accesoadatos.ConexionBaseDeDatos;

import java.sql.*;
import java.time.*;
import java.util.List;
import java.util.ArrayList;

public class ControladorVerificarDisponibilidadGUI {

    @FXML private DatePicker fechaPicker;
    @FXML private ComboBox<String> horaCombo;
    @FXML private TableView<VeterinarioDTO> tablaVeterinarios;

    private ProcedimientosDAO procedimientosDAO;
    private Utilidades utilidades = new Utilidades();

    @FXML
    public void initialize() {

        // Configurar fecha mínima (hoy)
        fechaPicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
    }

    @FXML
    private void verificarDisponibilidad() {
        if (fechaPicker.getValue() == null || horaCombo.getValue() == null) {
            utilidades.mostrarAlerta("Error", "Campos requeridos",
                    "Por favor seleccione una fecha y hora.");
            return;
        }

        try {
            // Crear Timestamp con la fecha y hora seleccionadas
            VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
            LocalDate fecha = fechaPicker.getValue();
            LocalTime hora = LocalTime.parse(horaCombo.getValue());
            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
            Timestamp timestamp = Timestamp.valueOf(fechaHora);

            // Obtener veterinarios disponibles
            List<VeterinarioDTO> veterinariosDisponibles = veterinarioDAO.obtenerVeterinariosDisponibles(timestamp);

            // Mostrar en la tabla
            ObservableList<VeterinarioDTO> datos = FXCollections.observableArrayList(veterinariosDisponibles);
            tablaVeterinarios.setItems(datos);

            if (veterinariosDisponibles.isEmpty()) {
                utilidades.mostrarAlerta("Información", "Disponibilidad",
                        "No hay veterinarios disponibles para la fecha y hora seleccionadas.");
            }

        } catch (Exception e) {
            utilidades.mostrarAlerta("Error", "Error al verificar disponibilidad",
                    "Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
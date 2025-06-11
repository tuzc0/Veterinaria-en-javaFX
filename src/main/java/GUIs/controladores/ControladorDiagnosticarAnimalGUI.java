package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logica.DAOs.*;
import logica.DTOs.*;
import java.sql.Date;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControladorDiagnosticarAnimalGUI {

    @FXML private TableView<CitaDTO> tablaCita;
    @FXML private TableColumn<CitaDTO, String> columnaFechaCita;
    @FXML private TableColumn<CitaDTO, String> columnaTipoCita;
    @FXML private TableColumn<CitaDTO, String> columnaMotivoCita;
    @FXML private Label etiquetaFecha;
    @FXML private Label etiquetaTipo;
    @FXML private Label etiquetaMotivo;
    @FXML private Label etiquetaEnfermedad;
    @FXML private Label etiquetaNombreAnimal;
    @FXML private ComboBox<String> comboEnfermedad;
    @FXML private Button botonDiagnosticar;
    @FXML private Button botonRegistrarEnfermedad;
    @FXML private Button botonRegresar;
    @FXML private DatePicker fechaCita;

    private Utilidades utilidades = new Utilidades();
    private CitaDTO citaSeleccionada;

    @FXML
    private void initialize() {

        columnaFechaCita.setCellValueFactory(Celldata ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(Celldata.getValue().getFecha())));
        columnaTipoCita.setCellValueFactory(Celldata ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(Celldata.getValue().getTipo())));
        columnaMotivoCita.setCellValueFactory(Celldata ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(Celldata.getValue().getMotivo())));

        tablaCita.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        cargarDatosTabla();

        tablaCita.getSelectionModel().selectedItemProperty().addListener((citaObservada,
                                                                          citaAnterior,
                                                                          nuevaSeleccionDeCita) -> {
            mostrarDetallesDesdeTabla(nuevaSeleccionDeCita);
            actualizarVisibilidadComponentes(nuevaSeleccionDeCita != null);
        });

        fechaCita.valueProperty().addListener((fechaObservada,
                                               FechaAnterior,
                                               nuevaFechaSeleccionada) -> {
            filtrarCitasPorFecha();
        });

        botonDiagnosticar.setCursor(Cursor.HAND);
        botonRegistrarEnfermedad.setCursor(Cursor.HAND);
        botonRegresar.setCursor(Cursor.HAND);
        fechaCita.setCursor(Cursor.HAND);

        actualizarVisibilidadComponentes(false);
    }

    private void cargarDatosTabla() {

        CitaDAO citaDAO = new CitaDAO();

        try {

            ObservableList<CitaDTO> citas =
                    FXCollections.observableArrayList(citaDAO.listarCitasSinDiagnostico());
            tablaCita.setItems(citas);

        } catch (SQLException e) {

            System.out.println("Error al cargar la lista de academicos: " + e);
            utilidades.mostrarAlerta(
                    "Error al cargar citas",
                    "No se pudo obtener la lista de citas.",
                    "Por favor, intentelo de nuevo más tarde o contacte al administrador.");

        } catch (IOException e) {

            System.out.println("Error al cargar la lista de academicos: " + e);
            utilidades.mostrarAlerta(
                    "Error al cargar citas",
                    "No se pudo obtener la lista de citas.",
                    "Por favor, revise su conexion a internet o intentelo de nuevo más tarde.");
        }
    }

    private void mostrarDetallesDesdeTabla(CitaDTO seleccionCita) {

        citaSeleccionada = seleccionCita;

        if (seleccionCita == null) {

            etiquetaFecha.setText("");
            etiquetaTipo.setText("");
            etiquetaMotivo.setText("");
            etiquetaNombreAnimal.setText("");
            return;
        }

        etiquetaFecha.setText(String.valueOf(seleccionCita.getFecha()));
        etiquetaTipo.setText(seleccionCita.getTipo());
        etiquetaMotivo.setText(seleccionCita.getMotivo());

        AnimalDAO animalDAO = new AnimalDAO();
        DueñoDAO dueñoDAO = new DueñoDAO();

        try {

            AnimalDTO animalDTO = animalDAO.buscarAnimalPorId(seleccionCita.getIdAnimal());
            DueñoDTO dueñoDTO = dueñoDAO.buscarDueñoPorId(animalDTO.getIdDueño());

            etiquetaNombreAnimal.setText(dueñoDTO.getNombre() + " " + dueñoDTO.getApellidos());

        } catch (SQLException e) {

            System.out.println(e);
            utilidades.mostrarAlerta(
                    "Error",
                    "Ocurrio un error al recuperar los datos del dueño.",
                    "Por favor, intentelo de nuevo más tarde."
            );

        } catch (IOException e) {

            System.out.println(e);
            utilidades.mostrarAlerta(
                    "Error",
                    "Ocurrio un error al cargar los datos del dueño.",
                    "Por favor, intentelo de nuevo más tarde."
            );
        }
    }

    @FXML
    private void filtrarCitasPorFecha() {

        if (fechaCita.getValue() != null) {

            CitaDAO citaDAO = new CitaDAO();

            try {

                List<CitaDTO> citas = citaDAO.obtenerCitasPorFecha(Date.valueOf(fechaCita.getValue()));
                tablaCita.getItems().setAll(citas);

                actualizarVisibilidadComponentes(false);

            } catch (SQLException e) {

                System.out.println(e);
                utilidades.mostrarAlerta(
                        "Error",
                        "Ocurrió un error al filtrar las citas.",
                        "Por favor, inténtelo de nuevo más tarde."
                );

            } catch (IOException e) {

                System.out.println(e);
                utilidades.mostrarAlerta(
                        "Error",
                        "Ocurrio un error al cargar las citas.",
                        "Por favor, revise su conexión a internet o íntentelo de nuevo más tarde."
                );
            }

        } else {

            cargarDatosTabla();
        }
    }

    private void actualizarVisibilidadComponentes(boolean visible) {

        etiquetaEnfermedad.setVisible(visible);
        comboEnfermedad.setVisible(visible);
        botonRegistrarEnfermedad.setVisible(visible);
        botonDiagnosticar.setVisible(visible);

        if (visible) {
            cargarEnfermedadesEnComboBox();
        } else {
            comboEnfermedad.getItems().clear();
        }
    }

    @FXML
    private void diagnosticar() {

        String enfermedadSeleccionada = comboEnfermedad.getValue();

        if (enfermedadSeleccionada == null || enfermedadSeleccionada.isEmpty()) {

            utilidades.mostrarAlerta(
                    "Error",
                    "Se necesita seleccionar una enfermedad.",
                    "Por favor, seleccione una enfermedad."
            );
            return;
        }

        utilidades.mostrarAlertaConfirmacion(

                "Confirmación de diagnóstico",
                "¿Está seguro de asignar este diagnóstico?",
                "Enfermedad seleccionada: " + enfermedadSeleccionada,

                () -> {

                    EnfermedadesDAO enfermedadesDAO = new EnfermedadesDAO();
                    DiagnosticaDAO diagnosticaDAO = new DiagnosticaDAO();

                    try {

                        EnfermedadesDTO enfermedadesDTO =
                                enfermedadesDAO.buscarEnfermedadPorNombre(enfermedadSeleccionada);
                        DiagnosticaDTO diagnosticoDTO =
                                new DiagnosticaDTO(citaSeleccionada.getIdCita(), enfermedadesDTO.getIdEnfermedad());

                        boolean diagnosticoExitoso = diagnosticaDAO.insertarDiagnostico(diagnosticoDTO);

                        if (diagnosticoExitoso) {

                            utilidades.mostrarAlerta(
                                    "Éxito",
                                    "El diagnóstico se realizó de forma exitosa.",
                                    ""
                            );

                            cargarDatosTabla();

                        } else {

                            utilidades.mostrarAlerta(
                                    "Error",
                                    "Ocurrió un error al registrar el diagnóstico.",
                                    "Por favor, contacte al administrador."
                            );
                        }

                    } catch (SQLException e) {

                        System.out.println(e);
                        utilidades.mostrarAlerta(
                                "Error",
                                "Ocurrió al diagnosticar",
                                "Por favor, inténtelo de nuevo más tarde."
                        );

                    } catch (IOException e) {

                        utilidades.mostrarAlerta(
                                "Error",
                                "Ocurrio al diagnosticar.",
                                "Por favor, revise su conexión a internet o íntentelo de nuevo más tarde."
                        );
                    }
                },
                () -> {

                    utilidades.mostrarAlerta(
                            "Cancelado",
                            "El diagnóstico no se realizó.",
                            ""
                    );
                }
        );
    }

    private void cargarEnfermedadesEnComboBox() {

        EnfermedadesDAO enfermedadesDAO = new EnfermedadesDAO();

        try {

            List<EnfermedadesDTO> enfermedadesDTOS = enfermedadesDAO.listarEnfermedades();
            comboEnfermedad.getItems().clear();

            for (EnfermedadesDTO enfermedad : enfermedadesDTOS) {

                comboEnfermedad.getItems().add(enfermedad.getNombre());
            }

        } catch (SQLException e) {

            System.out.println(e);
            utilidades.mostrarAlerta(
                    "Error",
                    "Ocurrió un error al cargar las enfermedades.",
                    "Por favor, inténtelo de nuevo más tarde."
            );

        } catch (IOException e) {

            System.out.println(e);
            utilidades.mostrarAlerta(
                    "Error",
                    "Ocurrió un error al cargar las enfermedades.",
                    "Por favor, revise su conexión a internet o inténtelo de nuevo más tarde."
            );
        }
    }

    @FXML
    private void abrirVentanaNueva() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AñadirEnfermedadACatalogo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Añadir Enfermedad al Catálogo");
            stage.show();

        } catch (IOException e) {

            System.out.println("Error al abrir la ventana: " + e);
            utilidades.mostrarAlerta(
                    "Error",
                    "No se pudo abrir la ventana.",
                    "Por favor, inténtelo de nuevo más tarde."
            );
        }
    }

    @FXML
    private void regresar() {

        botonRegresar.getScene().getWindow().hide();
    }
}

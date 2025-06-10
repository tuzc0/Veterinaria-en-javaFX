package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import logica.DAOs.AnimalDAO;
import logica.DAOs.DueñoDAO;
import logica.DTOs.AnimalDTO;
import logica.DTOs.DueñoDTO;
import logica.Validaciones;

import java.io.IOException;
import java.sql.SQLException;

public class ControladorNuevoAnimalGUI {

    @FXML private ImageView imagen;
    @FXML private TextField colorTX;
    @FXML private TextField razaTX;
    @FXML private ComboBox<String> comboTamaño;
    @FXML private Spinner<String> SpinnerPeso;
    @FXML private RadioButton botonPerro;
    @FXML private RadioButton botonGato;
    @FXML private TextField duenoTX;
    @FXML private Button cancelarBT;
    private ToggleGroup grupoAnimales;

    Utilidades utilidades = new Utilidades();

    @FXML
    public void initialize() {

        try {
            imagen.setImage(new Image("https://cdn-icons-png.flaticon.com/512/616/616408.png"));
        } catch (Exception e) {
            utilidades.mostrarAlerta("Error", "Error de conexión", "Ocurrió un error al cargar parte de la interfaz.");
        }

        grupoAnimales = new ToggleGroup();
        botonGato.setToggleGroup(grupoAnimales);
        botonPerro.setToggleGroup(grupoAnimales);


        configurarSpinnerPeso();

        cargarComboTamaño();
    }

    private void configurarSpinnerPeso() {

        SpinnerValueFactory<String> factory = new SpinnerValueFactory<String>() {
            private double currentValue = 0.0;

            @Override
            public void decrement(int steps) {
                currentValue = Math.max(0, currentValue - (0.1 * steps));
                setValue(String.format("%.1f", currentValue));
            }

            @Override
            public void increment(int steps) {
                currentValue += 0.1 * steps;
                setValue(String.format("%.1f", currentValue));
            }
        };


        SpinnerPeso.setValueFactory(factory);


        factory.setValue("0.0");


        SpinnerPeso.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty()) {
                    double value = Double.parseDouble(newValue);
                    if (value >= 0) {
                        factory.setValue(String.format("%.1f", value));
                    }
                }
            } catch (NumberFormatException e) {

                SpinnerPeso.getEditor().setText(oldValue);
            }
        });
    }

    public void cargarComboTamaño() {

        comboTamaño.getItems().clear();
        comboTamaño.getItems().addAll("Pequeño", "Mediano", "Grande");

    }

    @FXML
    private void registrarAnimal() {

        try {

            String color = colorTX.getText().trim();
            String raza = razaTX.getText().trim();
            String tamaño = comboTamaño.getValue();
            String peso = SpinnerPeso.getValue();
            String dueno = duenoTX.getText().trim();

            validarCampos();

            DueñoDAO dueñoDAO = new DueñoDAO();
            DueñoDTO dueñoDTO = dueñoDAO.buscarDueñoPorTelefono(dueno);

            if (dueñoDTO == null) {
                utilidades.mostrarAlerta("Error", "Dueño no encontrado", "El dueño especificado no existe.");
                return;
            }

            String tipoAnimal = obtenerTipoAnimal();

            if (tipoAnimal == null) {
                utilidades.mostrarAlerta("Error", "Tipo de animal no seleccionado", "Por favor, seleccione un tipo de animal.");
                return;
            }

            AnimalDTO nuevoAnimal = new AnimalDTO(
                    0,
                    color,
                    raza,
                    tamaño,
                    peso,
                    tipoAnimal,
                    dueñoDTO.getIdDueño()
            );

            AnimalDAO animalDAO = new AnimalDAO();
            animalDAO.insertarAnimal(nuevoAnimal);

            utilidades.mostrarAlerta("Éxito", "Registro exitoso", "El animal ha sido registrado correctamente.");

        } catch (SQLException e) {
            utilidades.mostrarAlerta("Error de base de datos", "Error al registrar el animal", e.getMessage());
        } catch (IOException e) {
            utilidades.mostrarAlerta("Error de IO", "Error al cargar la interfaz", e.getMessage());
        }


    }

    private String obtenerTipoAnimal() {

        if (grupoAnimales.getSelectedToggle() == null) {
            return null;
        }

        RadioButton seleccionado = (RadioButton) grupoAnimales.getSelectedToggle();
        return seleccionado.getText();
    }



    public boolean validarCampos() {

        Validaciones validaciones = new Validaciones();

        String color = colorTX.getText().trim();
        String raza = razaTX.getText().trim();
        String tamaño = comboTamaño.getValue();
        String peso = SpinnerPeso.getValue();
        String dueno = duenoTX.getText().trim();

        if (color.isEmpty() || raza.isEmpty() || tamaño == null || peso == null || dueno.isEmpty()) {
            utilidades.mostrarAlerta("Error", "Campos incompletos", "Por favor, complete todos los campos.");
            return false;
        }

        if (!validaciones.validarSoloAlfabeticos(color)) {
            utilidades.mostrarAlerta("Error", "Color inválido", "El color contiene caracteres no permitidos.");
            return false;
        }

        if (!validaciones.validarSoloAlfabeticos(raza)) {
            utilidades.mostrarAlerta("Error", "Raza inválida", "La raza contiene caracteres no permitidos.");
            return false;
        }

        if (!validaciones.validarNumeroTelefono(dueno)) {
            utilidades.mostrarAlerta("Error", "Dueño inválido", "El dueño contiene caracteres no permitidos.");
            return false;
        }

        return true;


    }



    @FXML
    private void cancelarRegistro() {


        utilidades.mostrarAlertaConfirmacion(
                "Confirmar eliminación",
                "¿Está seguro que desea cancelar el registro?",
                "Se cancelara . Esta acción no se puede deshacer.",
                () -> {

                    Stage stage = (Stage) cancelarBT.getScene().getWindow();
                    stage.close();

                },
                () -> {
                    utilidades.mostrarAlerta("Cancelado",
                            "Eliminación cancelada",
                            "No se ha cancelado ningun registro.");
                }

        );

    }


}
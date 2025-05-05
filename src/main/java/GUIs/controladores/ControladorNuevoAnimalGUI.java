package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logica.DAOs.AnimalDAO;
import logica.DAOs.DueñoDAO;
import logica.DTOs.AnimalDTO;
import logica.DTOs.DueñoDTO;
import java.io.IOException;
import java.sql.SQLException;

public class ControladorNuevoAnimalGUI {

    @FXML private ImageView imagen;
    @FXML private TextField colorTX;
    @FXML private TextField razaTX;
    @FXML private TextField tamanoTX;
    @FXML private TextField pesoTX;
    @FXML private TextField especieTX;
    @FXML private TextField duenoTX;
    @FXML private Button cancelarBT;

    @FXML
    public void initialize() {

        try {

            imagen.setImage(new Image("https://cdn-icons-png.flaticon.com/512/616/616408.png"));

        } catch (Exception e) {

            mostrarAlerta("Error", "Error de conexión", "Ocurrió un error al cargar parte de la interfaz.");
        }
    }

    @FXML
    private void registrarAnimal() {

        String color = colorTX.getText().trim();
        String raza = razaTX.getText().trim();
        String tamano = tamanoTX.getText().trim();
        String peso = pesoTX.getText().trim();
        String especie = especieTX.getText().trim();
        String dueno = duenoTX.getText().trim();

        if (color.isEmpty() || raza.isEmpty() || tamano.isEmpty() || peso.isEmpty() || especie.isEmpty() || dueno.isEmpty()) {

            mostrarAlerta("Error", "Campos vacíos", "Por favor, complete todos los campos.");
            return;
        }

        try {

            DueñoDAO dueñoDAO = new DueñoDAO();
            DueñoDTO dueñoDTO = dueñoDAO.buscarDueñoPorNombre(dueno);

            if (dueñoDTO.getIdDueño() == -1) {

                mostrarAlerta("Error", "Dueño no registrado", "Por favor registre al dueño antes. ");

            } else {

                int idDueno = dueñoDTO.getIdDueño();
                AnimalDAO animalDAO = new AnimalDAO();
                AnimalDTO nuevoAnimal = new AnimalDTO(0, color, raza, tamano, peso, especie, idDueno);
                boolean exito = animalDAO.insertarAnimal(nuevoAnimal);

                if (exito) {

                    mostrarAlerta("Éxito", "Registro exitoso", "El animal ha sido registrado correctamente.");
                    limpiarCampos();

                } else {

                    mostrarAlerta("Error", "Registro fallido", "No se pudo registrar el animal.");
                }
            }

        } catch (NumberFormatException e) {

            mostrarAlerta("Error", "Formato inválido", "El ID del dueño debe ser un número.");

        } catch (SQLException | IOException e) {

            mostrarAlerta("Error", "Error de conexión", "No se pudo conectar a la base de datos.");
        }
    }

    private void limpiarCampos() {

        colorTX.clear();
        razaTX.clear();
        tamanoTX.clear();
        pesoTX.clear();
        especieTX.clear();
        duenoTX.clear();
    }

    @FXML
    private void cancelarRegistro() {
        Stage stage = (Stage) cancelarBT.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String cabecera, String contenido) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
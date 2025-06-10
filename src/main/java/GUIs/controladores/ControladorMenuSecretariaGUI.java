package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ControladorMenuSecretariaGUI {

    Utilidades utilidades = new Utilidades();

    @FXML
    public void abrirVentanaRegistroCita() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/insertarCitaGUI.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("Registrar Cita");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            utilidades.mostrarAlerta(
                "Error al abrir la ventana de registro de cita",
                "Error al abrir la ventana de registro de cita",
                "No se pudo cargar la ventana de registro de cita. Por favor, inténtelo de nuevo más tarde."
            );

        }
    }
}

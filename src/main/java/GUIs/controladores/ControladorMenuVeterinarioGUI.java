package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import GUIauxiliar.Utilidades;

import java.io.IOException;

public class ControladorMenuVeterinarioGUI {

    @FXML private Button botonCita;

    private Utilidades utilidades = new Utilidades();

    @FXML
    private void initialize() {
        botonCita.setOnAction(event -> abrirVentanaDiagnosticarAnimal());
    }

    @FXML
    private void abrirVentanaDiagnosticarAnimal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DiagnosticarAnimal.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Diagnosticar Animal");
            stage.show();
        } catch (IOException e) {
            utilidades.mostrarAlerta(
                    "Error",
                    "No se pudo abrir la ventana de Diagnosticar Animal.",
                    "Por favor, inténtelo de nuevo más tarde."
            );
        }
    }
}
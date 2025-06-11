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

    @FXML
    public void abrirVentanaContarCitas() {

        utilidades.mostrarVentana("/ContarCitas.fxml");
    }

    @FXML
    public void abrirVentanaContarProductos() {

        utilidades.mostrarVentana("/ContarProductosGUI.fxml");
    }

    @FXML
    public void abrirVentanaRegistrarCompra() {

        utilidades.mostrarVentana("/RegistrarCompraGUI.fxml");
    }

    @FXML
    public void abrirVentanaVerificarVeterinarios() {

        utilidades.mostrarVentana("/VerificarVeterinariosGUI.fxml");
    }

    @FXML
    public void abrirVentanaGestorProductos() {

        utilidades.mostrarVentana("/GestorProducto.fxml");
    }

    @FXML
    public void abrirVentanaProductosPendientes() {
        utilidades.mostrarVentana("/ListaProductosPendientes.fxml");
    }

    @FXML
    public void abrirVentanaReporteVentas() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reporte_ventas.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Reportes de Ventas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            utilidades.mostrarAlerta(
                    "Error al abrir la ventana de reportes de ventas",
                    "Error al abrir la ventana de reportes de ventas",
                    "No se pudo cargar la ventana de reportes de ventas. Por favor, inténtelo de nuevo más tarde."
            );
        }
    }
}

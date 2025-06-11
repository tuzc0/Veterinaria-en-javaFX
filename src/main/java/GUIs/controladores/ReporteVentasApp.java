package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logica.DAOs.ReporteVentasDAO;
import accesoadatos.ConexionBaseDeDatos;

public class ReporteVentasApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Utilidades utilidades = new Utilidades();

        try {

            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            ReporteVentasDAO reporteDAO = new ReporteVentasDAO(conexionBD);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reporte_ventas.fxml"));
            Parent root = loader.load();

            ControladorReporteVentas controller = loader.getController();
            controller.setReporteDAO(reporteDAO);

            Scene scene = new Scene(root, 600, 400);
            primaryStage.setTitle("Reportes de Ventas");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {

            utilidades.mostrarAlerta(
                    "Error al iniciar",
                    "Error al cargar la ventana",
                    "No se pudo iniciar la aplicación: " + e
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
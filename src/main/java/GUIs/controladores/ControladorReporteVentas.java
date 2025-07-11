package GUIs.controladores;

import GUIauxiliar.Utilidades;
import accesoadatos.ConexionBaseDeDatos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import logica.DAOs.ReporteVentasDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControladorReporteVentas {
    @FXML private ComboBox<Integer> cmbMes;
    @FXML private Spinner<Integer> spnAnio;

    private ReporteVentasDAO reporteDAO;
    private Utilidades utilidades;

    @FXML
    public void initialize() {

        utilidades = new Utilidades();
        try {
            ConexionBaseDeDatos conexionBD = new ConexionBaseDeDatos();
            reporteDAO = new ReporteVentasDAO(conexionBD);
        } catch (SQLException e){
            utilidades.mostrarAlerta(
                    "Error de Conexión",
                    "No se pudo establecer conexión con la base de datos.",
                    "Por favor, verifica tu conexión e intenta nuevamente."
            );
            return;
        } catch (IOException e) {
            utilidades.mostrarAlerta(
                    "Error de Conexión",
                    "Ocurrió un error al intentar conectarse a la base de datos.",
                    "Por favor, verifica tu conexión e intenta nuevamente."
            );
            return;
        } catch (Exception e) {
            utilidades.mostrarAlerta(
                    "Error Desconocido",
                    "Ocurrió un error inesperado.",
                    "Por favor, contacta al soporte técnico."
            );
            return;
        }


        for (int i = 1; i <= 12; i++) {
            cmbMes.getItems().add(i);
        }
        cmbMes.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);

        SpinnerValueFactory<Integer> yearFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(2000, 2100, LocalDate.now().getYear());
        spnAnio.setValueFactory(yearFactory);
    }

    public void setReporteDAO(ReporteVentasDAO reporteDAO) {
        this.reporteDAO = reporteDAO;
    }

    @FXML
    private void mostrarProductoMasVendido() {

        String resultado = reporteDAO.obtenerProductoMasVendido(
                cmbMes.getValue(),
                spnAnio.getValue()
        );
        utilidades.mostrarAlerta(
                "Producto Más Vendido",
                "Resultado para el mes: " + cmbMes.getValue() + " del año: " + spnAnio.getValue(),
                resultado
        );
    }

    @FXML
    private void mostrarProductoMenosVendido() {

        String resultado = reporteDAO.obtenerProductoMenosVendido(
                cmbMes.getValue(),
                spnAnio.getValue()
        );
        utilidades.mostrarAlerta(
                "Producto Menos Vendido",
                "Resultado para el mes: " + cmbMes.getValue() + " del año: " + spnAnio.getValue(),
                resultado
        );
    }

    @FXML
    private void mostrarPromedioVentas() {
        String resultado = reporteDAO.obtenerPromedioVentas(
                cmbMes.getValue(),
                spnAnio.getValue()
        );
        utilidades.mostrarAlerta(
                "Promedio de Ventas",
                "Resultado para el mes: " + cmbMes.getValue() + " del año:" + spnAnio.getValue(),
                resultado
        );
    }

    @FXML
    private void mostrarSumaTotalVentas() {
        String resultado = reporteDAO.obtenerSumaTotalVentas(
                cmbMes.getValue(),
                spnAnio.getValue()
        );
        utilidades.mostrarAlerta(
                "Suma Total de Ventas",
                "Resultado para el mes: " + cmbMes.getValue() + " del año: " + spnAnio.getValue(),
                resultado
        );
    }
}
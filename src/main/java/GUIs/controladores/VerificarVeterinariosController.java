package GUIs.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logica.DAOs.ProcedimientosDAO;
import accesoadatos.ConexionBaseDeDatos;
import java.sql.*;
import java.time.LocalDateTime;

public class VerificarVeterinariosController {
    @FXML private DatePicker fechaPicker;
    @FXML private TextField horaTX;
    @FXML private TableView<VeterinarioInfo> tablaVet;
    @FXML private TableColumn<VeterinarioInfo, String> colNombre;
    @FXML private TableColumn<VeterinarioInfo, String> colApellido;



    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
    }

    @FXML
    private void verificarVeterinarios() {

        ObservableList<VeterinarioInfo> datos = FXCollections.observableArrayList();
        try {
            Timestamp ts = Timestamp.valueOf(fechaPicker.getValue().atTime(Integer.parseInt(horaTX.getText().split(":")[0]),
                    Integer.parseInt(horaTX.getText().split(":")[1])));
            String sql = "{CALL verificarVeterinariosDisponibles(?)}";
            try (Connection conn = new ConexionBaseDeDatos().getConnection();
                 CallableStatement stmt = conn.prepareCall(sql)) {
                stmt.setTimestamp(1, ts);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    datos.add(new VeterinarioInfo(rs.getString("nombre"), rs.getString("apellidos")));
                }
            }
            tablaVet.setItems(datos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class VeterinarioInfo {
        private final String nombre;
        private final String apellidos;

        public VeterinarioInfo(String nombre, String apellidos) {
            this.nombre = nombre;
            this.apellidos = apellidos;
        }

        public String getNombre() { return nombre; }
        public String getApellidos() { return apellidos; }
    }
}

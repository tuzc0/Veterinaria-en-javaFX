package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logica.DAOs.SecretarioDAO;
import logica.DAOs.VeterinarioDAO;
import logica.DTOs.SecretarioDTO;
import logica.DTOs.VeterinarioDTO;
import java.io.IOException;
import java.sql.SQLException;

public class ControladorLoginGUI {

    @FXML private ImageView imagen;
    @FXML private ImageView personaIcono;
    @FXML private ImageView llaveIcono;
    @FXML private TextField nombreUsuarioTX;
    @FXML private PasswordField contrasenaTX;

    static int idSecretario = -1;
    static int idVeterinario = -1;

    Utilidades utilidades = new Utilidades();

    @FXML
    public void initialize() {

        try {

            imagen.setImage(new Image("https://cdn-icons-png.flaticon.com/512/1263/1263052.png"));
            personaIcono.setImage(new Image("https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png"));
            llaveIcono.setImage(new Image("https://cdn-icons-png.flaticon.com/512/3064/3064197.png"));
        } catch (Exception e) {

            mostrarAlerta("Error", "Error de conexión", "Ocurrio un error al cargar parte de la interfaz. ");
        }
    }

    @FXML
    private void autenticarUsuario() {

        String usuario = nombreUsuarioTX.getText().trim();
        String contrasena = contrasenaTX.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {

            mostrarAlerta("Error", "Campos vacíos", "Por favor, complete todos los campos.");
            return;
        }

        try {

            SecretarioDAO secretarioDAO = new SecretarioDAO();
            VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

            SecretarioDTO secretario = secretarioDAO.buscarSecretarioPorUsuarioYContraseña(usuario, contrasena);

            if (secretario.getIdSecretario() != -1) {

                idSecretario = secretario.getIdSecretario();
                mostrarAlerta("Éxito", "Inicio de sesión correcto", "Bienvenido, " + secretario.getNombre() + " (Secretario).");
                utilidades.mostrarVentana("/MenuSecretariaGUI.fxml");
                return;
            }

            VeterinarioDTO veterinario = veterinarioDAO.buscarVeterinarioPorUsuarioYContraseña(usuario, contrasena);

            if (veterinario.getIdVeterinario() != -1) {

                idVeterinario = veterinario.getIdVeterinario();
                mostrarAlerta("Éxito", "Inicio de sesión correcto", "Bienvenido, " + veterinario.getNombre() + " (Veterinario).");
                utilidades.mostrarVentana("/MenuVeterinario.fxml");
                return;
            }

            if (usuario.equals("admin")&& contrasena.equals("admin")) {

                mostrarAlerta("Éxito", "Inicio de sesión correcto", "Bienvenido, Administrador.");
                utilidades.mostrarVentana("/MenuAdminGUI.fxml");
                return;
            }

            mostrarAlerta("Error", "Credenciales incorrectas", "Usuario o contraseña incorrectos.");

        } catch (SQLException | IOException e) {

            mostrarAlerta("Error", "Error de conexión", "No se pudo conectar a la base de datos ");
            e.printStackTrace();

        }
    }

    private void mostrarAlerta(String titulo, String cabecera, String contenido) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecera);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}


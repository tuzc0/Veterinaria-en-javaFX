package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logica.DAOs.EnfermedadesDAO;
import logica.DTOs.EnfermedadesDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorAñadirEnfermedadACatalogoGUI {

    @FXML private TextField campoNombre;
    @FXML private TextField campoEspecie;
    @FXML private TextField campoTratamiento;
    @FXML private Button botonAnadir;
    @FXML private Button botonRegresar;

    private Utilidades utilidades = new Utilidades();

    @FXML
    private void initialize() {

        botonAnadir.setCursor(Cursor.HAND);
        botonRegresar.setCursor(Cursor.HAND);
    }

    @FXML
    private void añadirEnfermedadACatalogo() {

        String nombre = campoNombre.getText();
        String especie = campoEspecie.getText();
        String tratamiento = campoTratamiento.getText();

        List<String> camposVacios = validarDatosEnfermedad(nombre, especie, tratamiento);

        if (!camposVacios.isEmpty()) {

            String mensajeError = String.join("\n", camposVacios);

            utilidades.mostrarAlerta(
                    "Campos vacios",
                    "Es necesario el llenado de todos los campos.",
                    mensajeError
            );
            return;
        }

        List<String> datosInvalidos = validarDatosIngresados(nombre, especie, tratamiento);

        if (!datosInvalidos.isEmpty()) {

            String datoInvalido = String.join("\n", datosInvalidos);
            utilidades.mostrarAlerta(
                    "Datos invalidos",
                    "Algunos datos ingresados son invalidos.",
                    datoInvalido
            );
            return;
        }

        int idEnfermedad = 0;
        EnfermedadesDAO enfermedadesDAO = new EnfermedadesDAO();
        EnfermedadesDTO enfermedadesDTO = new EnfermedadesDTO(
                idEnfermedad,
                nombre,
                especie,
                tratamiento
        );

        try {

            boolean enfermedadIngresada = enfermedadesDAO.insertarEnfermedad(enfermedadesDTO);

            if (enfermedadIngresada) {

                utilidades.mostrarAlerta(
                        "Exito",
                        "La enfermedad ha sido registrada dentro del sistema.",
                        ""
                );

            } else {

                utilidades.mostrarAlerta(
                        "Error",
                        "No se pudo añadir la enferdad dentro del sistema.",
                        "Por favor, contacte al administrador."
                );
            }

        } catch (SQLException e) {

            System.out.println(e);
            utilidades.mostrarAlerta(
                    "Error.",
                    "Ocurrio un error en el registro de la enfermedad.",
                    "Por favor, intentelo en otra ocasión"
            );

        } catch (IOException e) {

            System.out.println(e);
            utilidades.mostrarAlerta(
                    "Error.",
                    "Ocurrio un error durante el registro de la enfermedad.",
                    "Por favor, verifique su conexion a internet o intentelo de nuevo más tarde."
            );
        }
    }

    @FXML
    private void cancelarInsercion() {

        String nombre = campoNombre.getText();
        String especie = campoEspecie.getText();
        String tratamiento = campoTratamiento.getText();

        utilidades.mostrarAlertaConfirmacion(
                "Confirmacion de abandono",
                "¿Está seguro de querer abandonar el registro?",
                "Si cancela el ingreso de la enfermedad los datos no se reflejaran en el sistema.",

                () -> {

                    botonRegresar.getScene().getWindow().hide();
                },
                () -> {

                    campoNombre.setText(nombre);
                    campoEspecie.setText(especie);
                    campoTratamiento.setText(tratamiento);
                }
        );
    }

    private List<String> validarDatosEnfermedad(String nombre, String especie, String tratamiento) {

        List<String> errores = new ArrayList<>();

        if (nombre.isEmpty()) {
            errores.add("El campo nombre no puede estar vacio.");
        }

        if (especie.isEmpty()) {
            errores.add("El campo especie no puede estar vacio.");
        }

        if (tratamiento.isEmpty()) {
            errores.add("El campo de tratamiento no puede estar vacio");
        }

        return errores;
    }

    private List<String> validarDatosIngresados(String nombre, String especie, String tratamiento) {

        List<String> errores = new ArrayList<>();
        String VALORES_ADMITIDOS = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";

        if (!nombre.matches(VALORES_ADMITIDOS)) {
            errores.add("El campo nombre debe contener exclusivamente letras.");
        } else if (nombre.length() > 255) {
            errores.add("El campo nombre no puede exceder los 255 caracteres.");
        }

        if (!especie.matches(VALORES_ADMITIDOS)) {
            errores.add("El campo especie debe contener exclusivamente letras.");
        } else if (especie.length() > 255) {
            errores.add("El campo especie no puede exceder los 255 caracteres.");
        }

        if (!tratamiento.matches(VALORES_ADMITIDOS)) {
            errores.add("El campo tratamiento debe contener exclusivamente letras.");
        } else if (tratamiento.length() > 255) {
            errores.add("El campo tratamiento no puede exceder los 255 caracteres.");
        }

        return errores;
    }
}

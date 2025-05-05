package GUIs.controladores;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logica.DAOs.AnimalDAO;
import logica.DTOs.AnimalDTO;
import java.io.IOException;
import java.sql.SQLException;

public class ControladorGestorAnimalGUI {

    @FXML private TextField campoBusquedaAnimal;
    @FXML private Button botonBuscarAnimal;
    @FXML private TextField campoEspecieEditable;
    @FXML private Label campoEspecieEncontrada;
    @FXML private Label campoRazaEncontrada;
    @FXML private TextField campoRazaEditable;
    @FXML private Label campoColorEncontrado;
    @FXML private TextField campoColorEditable;
    @FXML private Label campoTamañoEncontrado;
    @FXML private TextField campoTamañoEditable;
    @FXML private Label campoPesoEncontrado;
    @FXML private TextField campoPesoEditable;
    @FXML private Label campoDueñoEncontrado;
    @FXML private TextField campoDueñoEditable;
    @FXML private Button botonEditarAnimal;
    @FXML private Button botonEliminarAnimal;
    @FXML private Button botonRegistrarAnimal;
    @FXML private Button botonEliminarSeleccionado;
    @FXML private Button botonGuardar;
    @FXML private Button botonCancelar;
    @FXML private Label campoNumeroAnimalesSeleccionados;
    @FXML private TableView<AnimalDTO> tablaAnimales;
    @FXML private TableColumn<AnimalDTO, String> columnaEspecie;
    @FXML private TableColumn<AnimalDTO, String> columnaRaza;
    @FXML private TableColumn<AnimalDTO, String> columnaColor;
    @FXML private TableColumn<AnimalDTO, String> columnaTamaño;
    @FXML private TableColumn<AnimalDTO, String> columnaPeso;
    @FXML private TableColumn<AnimalDTO, Integer> columnaDueño;

    private int idAnimal = 0;

    @FXML
    public void initialize() {

        columnaEspecie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspecie()));
        columnaRaza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaza()));
        columnaColor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
        columnaTamaño.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTamaño()));
        columnaPeso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPeso()));
        columnaDueño.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getIdDueño()));

        cargarAnimales();
        tablaAnimales.getSelectionModel().getSelectionMode();

        tablaAnimales.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            mostrarDetallesDesdeTabla(newSel);
            botonEliminarSeleccionado.setDisable(newSel == null);
        });
    }

    private void cargarAnimales() {

        try {

            AnimalDAO animalesDAO = new AnimalDAO();
            ObservableList<AnimalDTO> animalesDTO = FXCollections.observableArrayList(animalesDAO.listarAnimales());
            tablaAnimales.setItems(animalesDTO);

        } catch (SQLException e) {

            mostrarAlerta("Error de Base de Datos", "No se pudieron cargar los animales", e.getMessage());

        } catch (IOException e) {

            mostrarAlerta("Error de Entrada/Salida", "No se pudieron cargar los animales", e.getMessage());
        }
    }

    @FXML
    private void buscarAnimal() {

        String especie = campoEspecieEncontrada.getText().trim();

        if (especie.isEmpty()) {

            mostrarAlerta("Error", "El campo de búsqueda se encuentra vacío", "Por favor llene el campo de búsqueda con la especie que desea buscar.");
            return;
        }

        try {

            AnimalDAO animalDAO = new AnimalDAO();
            AnimalDTO animalDTO = animalDAO.buscarAnimalPorId(Integer.parseInt(campoBusquedaAnimal.getText().trim()));

            if (animalDTO.getIdAnimal() != -1) {

                campoEspecieEncontrada.setText(animalDTO.getEspecie());
                campoRazaEncontrada.setText(animalDTO.getRaza());
                campoColorEncontrado.setText(animalDTO.getColor());
                campoTamañoEncontrado.setText(animalDTO.getTamaño());
                campoPesoEncontrado.setText(animalDTO.getPeso());
                campoDueñoEncontrado.setText(String.valueOf(animalDTO.getIdDueño()));

            } else {

                mostrarAlerta("Error", "Animal no encontrado", "No se encontró un animal con el ID proporcionado.");
            }

        } catch (NumberFormatException e) {

            mostrarAlerta("Error", "Formato inválido", "El ID del animal debe ser un número.");

        } catch (SQLException | IOException e) {

            mostrarAlerta("Error", "Error al buscar", "Ocurrió un error al intentar buscar el animal.");
        }
    }


    private void mostrarDetallesDesdeTabla(AnimalDTO animalSeleccionado) {

        if (animalSeleccionado == null) return;

        try {

            AnimalDAO animalDAO = new AnimalDAO();
            AnimalDTO animalDTO = animalDAO.buscarAnimalPorId(animalSeleccionado.getIdAnimal());

            if (animalDTO.getIdAnimal() != -1) {

                campoEspecieEncontrada.setText(animalDTO.getEspecie());
                campoRazaEncontrada.setText(animalDTO.getRaza());
                campoColorEncontrado.setText(animalDTO.getColor());
                campoTamañoEncontrado.setText(animalDTO.getTamaño());
                campoPesoEncontrado.setText(animalDTO.getPeso());
                campoDueñoEncontrado.setText(String.valueOf(animalDTO.getIdDueño()));
            }

        } catch (SQLException e) {

            mostrarAlerta("Ocurrio un error intente mas tarde", "No se pudieron cargar los detalles del animal", e.getMessage());

        } catch (IOException e) {

            mostrarAlerta("Ocurrio un error intente mas tarde", "No se pudieron cargar los detalles del animal", e.getMessage());
        }
    }

    @FXML
    private void abrirVentanaRegistrarAnimal() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/nuevoAnimalGUI.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            stage.setOnHiding(event -> cargarAnimales());
            stage.show();

        } catch (IOException e) {

            mostrarAlerta("Error", "Ocurrió un error", "No se pudo cargar la ventana.");
        }
    }

    @FXML
    private void eliminarAnimal() {

        AnimalDTO animalSeleccionado = tablaAnimales.getSelectionModel().getSelectedItem();

        if (animalSeleccionado == null) {

            mostrarAlerta("Error", "No se seleccionó un animal", "Por favor, seleccione un animal de la tabla.");
            return;
        }

        String idAnimalTexto = campoDueñoEncontrado.getText().trim();

        if (idAnimalTexto.isEmpty()) {

            mostrarAlerta("Error", "Campo vacío", "Por favor seleccione un animal para eliminar.");
            return;
        }

        try {

            int idAnimal = Integer.parseInt(idAnimalTexto);
            AnimalDAO animalDAO = new AnimalDAO();
            boolean eliminado = animalDAO.eliminarAnimalPorId(idAnimal);

            if (eliminado) {

                mostrarAlerta("Éxito", "Animal eliminado", "El animal ha sido eliminado correctamente.");
                cargarAnimales();

            } else {

                mostrarAlerta("Error", "No eliminado", "No se pudo eliminar el animal.");
            }

        } catch (NumberFormatException e) {

            mostrarAlerta("Error", "Formato inválido", "El ID del animal no es válido.");

        } catch (SQLException | IOException e) {

            mostrarAlerta("Error", "Error al eliminar", "Ocurrió un error al intentar eliminar el animal.");
        }
    }

    @FXML
    private void editarAnimal() {

        AnimalDTO animalSeleccionado = tablaAnimales.getSelectionModel().getSelectedItem();

        if (animalSeleccionado == null) {

            mostrarAlerta("Error", "No se seleccionó un animal", "Por favor, seleccione un animal de la tabla.");
            return;
        }

        campoEspecieEditable.setText(campoEspecieEncontrada.getText());
        campoRazaEditable.setText(campoRazaEncontrada.getText());
        campoColorEditable.setText(campoColorEncontrado.getText());
        campoTamañoEditable.setText(campoTamañoEncontrado.getText());
        campoPesoEditable.setText(campoPesoEncontrado.getText());
        campoDueñoEditable.setText(campoDueñoEncontrado.getText());

        campoEspecieEditable.setVisible(true);
        campoRazaEditable.setVisible(true);
        campoColorEditable.setVisible(true);
        campoTamañoEditable.setVisible(true);
        campoPesoEditable.setVisible(true);
        campoDueñoEditable.setVisible(true);

        campoEspecieEncontrada.setVisible(false);
        campoRazaEncontrada.setVisible(false);
        campoColorEncontrado.setVisible(false);
        campoTamañoEncontrado.setVisible(false);
        campoPesoEncontrado.setVisible(false);
        campoDueñoEncontrado.setVisible(false);

        botonGuardar.setVisible(true);
        botonCancelar.setVisible(true);

        botonEditarAnimal.setVisible(false);
        botonEliminarAnimal.setVisible(false);
        tablaAnimales.setDisable(true);
        botonRegistrarAnimal.setDisable(true);
    }

    @FXML
    private void cancelarEdicionAnimal() {

        campoEspecieEditable.setVisible(false);
        campoRazaEditable.setVisible(false);
        campoColorEditable.setVisible(false);
        campoTamañoEditable.setVisible(false);
        campoPesoEditable.setVisible(false);
        campoDueñoEditable.setVisible(false);

        campoEspecieEncontrada.setVisible(true);
        campoRazaEncontrada.setVisible(true);
        campoColorEncontrado.setVisible(true);
        campoTamañoEncontrado.setVisible(true);
        campoPesoEncontrado.setVisible(true);
        campoDueñoEncontrado.setVisible(true);

        botonGuardar.setVisible(false);
        botonCancelar.setVisible(false);

        botonEditarAnimal.setVisible(true);
        botonEliminarAnimal.setVisible(true);
        tablaAnimales.setDisable(false);
        botonRegistrarAnimal.setDisable(false);
    }

    @FXML
    private void guardarCambiosAnimal() {

        String especie = campoEspecieEditable.getText().trim();
        String raza = campoRazaEditable.getText().trim();
        String color = campoColorEditable.getText().trim();
        String tamaño = campoTamañoEditable.getText().trim();
        String peso = campoPesoEditable.getText().trim();
        String idDueñoTexto = campoDueñoEditable.getText().trim();

        if (especie.isEmpty() || raza.isEmpty() || color.isEmpty() || tamaño.isEmpty() || peso.isEmpty() || idDueñoTexto.isEmpty()) {

            mostrarAlerta("Error", "Campos vacíos", "Por favor, complete todos los campos.");
            return;
        }

        try {

            AnimalDTO animalSeleccionado = tablaAnimales.getSelectionModel().getSelectedItem();

            if (animalSeleccionado == null) {

                mostrarAlerta("Error", "No se seleccionó un animal", "Por favor, seleccione un animal de la tabla.");
                return;
            }

            int idAnimal = animalSeleccionado.getIdAnimal();
            int idDueño = Integer.parseInt(idDueñoTexto);

            AnimalDAO animalDAO = new AnimalDAO();
            AnimalDTO animalModificado = new AnimalDTO(
                    idAnimal, color, raza, tamaño, peso, especie, idDueño
            );

            boolean animal_modificado = animalDAO.modificarAnimal(animalModificado);

            if (!animal_modificado) {

                mostrarAlerta("Error", "No se pudo modificar el registro", "Por favor, intentelo nuevamente");

            } else {

                mostrarAlerta("Exito", "El animla se ha modificado con exito","El animal se ha modificado");
            }

            campoEspecieEncontrada.setText(especie);
            campoRazaEncontrada.setText(raza);
            campoColorEncontrado.setText(color);
            campoTamañoEncontrado.setText(tamaño);
            campoPesoEncontrado.setText(peso);
            campoDueñoEncontrado.setText(idDueñoTexto);

            cancelarEdicionAnimal();
            cargarAnimales();

        } catch (NumberFormatException e) {

            mostrarAlerta("Error", "Formato inválido", "El ID del dueño debe ser un número.");

        } catch (SQLException | IOException e) {

            mostrarAlerta("Error", "Error al guardar", "Ocurrió un error al intentar guardar los cambios.");
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

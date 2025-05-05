package GUIs.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML private Button botonSeleccionarAnimales;
    @FXML private Button botonEliminarSeleccionado;
    @FXML private Button botonGuardar;
    @FXML private Button botonCancelar;
    @FXML private Button botonCancelarSeleccion;
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
        columnaEspecie.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEspecie()));
        columnaRaza.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRaza()));
        columnaColor.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getColor()));
        columnaTamaño.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTamaño()));
        columnaPeso.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPeso()));
        columnaDueño.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdDueño()));

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

    private void mostrarDetallesDesdeTabla(AnimalDTO animalSeleccionado) {
        if (animalSeleccionado == null) return;

        try {

            AnimalDAO animalDAO = new AnimalDAO();
            AnimalDTO animalDTO = animalDAO.buscarAnimalPorId(animalSeleccionado.getIdAnimal());

            if (animalDTO.getIdAnimal() != -1) {

                campoEspecieEncontrada.setText(animalDTO.getEspecie());


            }

        } catch (SQLException e) {

            mostrarAlerta("Ocurrio un error intente mas tarde", "No se pudieron cargar los detalles del animal", e.getMessage());

        } catch (IOException e) {

            mostrarAlerta("Ocurrio un error intente mas tarde", "No se pudieron cargar los detalles del animal", e.getMessage());
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

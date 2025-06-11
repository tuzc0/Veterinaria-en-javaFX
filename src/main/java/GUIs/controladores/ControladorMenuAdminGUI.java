package GUIs.controladores;

import GUIauxiliar.Utilidades;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ControladorMenuAdminGUI {

    Utilidades utilidades = new Utilidades();


    @FXML
    private void abrirGestionVeterinario(){

        utilidades.mostrarVentana("/RegistrarUsuarioGUI.fxml");

    }

    @FXML
    private void abrirGestionSecretaria() {
        utilidades.mostrarVentana("/RegistrarUsuarioGUI.fxml");
    }



}

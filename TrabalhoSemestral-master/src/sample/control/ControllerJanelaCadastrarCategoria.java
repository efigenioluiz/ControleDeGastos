package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerJanelaCadastrarCategoria {
    @FXML
    TextField tfNomeCategoria;

    public String processResult(){
        return tfNomeCategoria.getText();
    }
}

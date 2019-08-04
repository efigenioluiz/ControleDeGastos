package sample.control;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.model.Categoria;
import sample.model.JDBC.JDBCCategoriaDAO;

public class ControllerJanelaCadastrarSubcategoria {
    public ComboBox<Categoria> cbCategoria;
    @FXML
    TextField tfNomeSubcategoria;



    public void initialize(){
        cbCategoria.getItems().addAll(JDBCCategoriaDAO.getInstance().listCategoria(ControllerJanelaPrincipal.LOGADO));
    }
    public String processResult(){
        return tfNomeSubcategoria.getText();
    }
    public Categoria processResult2(){
        return cbCategoria.getSelectionModel().getSelectedItem();
    }
}

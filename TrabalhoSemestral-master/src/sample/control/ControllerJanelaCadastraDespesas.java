package sample.control;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.model.Categoria;
import sample.model.Despesa;
import sample.model.JDBC.JDBCCategoriaDAO;
import sample.model.JDBC.JDBCDespesaDAO;
import sample.model.JDBC.JDBCSubcategoriaDAO;
import sample.model.Subcategoria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

public class ControllerJanelaCadastraDespesas {
    @FXML
    TextField tfNomeDespesa,tfValor;

    @FXML
    DatePicker tpData;


    @FXML
    ComboBox<Categoria> cbCategoria;

    @FXML
    ComboBox<Subcategoria> cbSubcategoria;

    public void initialize(){
        cbCategoria.getItems().addAll(JDBCCategoriaDAO.getInstance().listCategoria(ControllerJanelaPrincipal.LOGADO));
 }
    public Despesa processResult() throws Exception {

        Despesa d= new Despesa(tfNomeDespesa.getText(),Double.parseDouble(tfValor.getText()),tpData.getValue().toString(),cbCategoria.getValue(),false,cbSubcategoria.getValue());

        JDBCDespesaDAO.getInstance().create(d,d.getCategoria().getId(),d.subcategoria.getId());


        return d;
    }

    public void setSubCategoria() {
        cbSubcategoria.getItems().clear();
        cbSubcategoria.getItems().addAll(JDBCSubcategoriaDAO.getInstance().listSubcategoria(ControllerJanelaPrincipal.LOGADO,cbCategoria.getSelectionModel().getSelectedItem()));
    }
}

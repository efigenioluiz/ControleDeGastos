package sample.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Categoria;
import sample.model.Despesa;
import sample.model.JDBC.JDBCCategoriaDAO;
import sample.model.JDBC.JDBCDespesaDAO;
import sample.model.JDBC.JDBCUsuarioDAO;

public class ControllerJanelaVisualizarDespesas {
    @FXML
    public ComboBox<Categoria> cbCategoria;

    @FXML
    public TableView<Despesa> tvDespesasCat;

    @FXML
    public TableColumn<Despesa,String> tcNome,tcValor,tcCategoria,tcData;
    public TableColumn tcSubcategoria;


    public void initialize(){
        //carregar categoria na combo box
        cbCategoria.getItems().addAll(JDBCCategoriaDAO.getInstance().listCategoria(ControllerJanelaPrincipal.LOGADO));
    }

    public void ok(){
        tvDespesasCat.getItems().clear();

        tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeDespesa"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcSubcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));


        tvDespesasCat.setItems(JDBCDespesaDAO.getInstance().listDespesaCategoria(retCategoria(),ControllerJanelaPrincipal.LOGADO));
    }

    public Categoria retCategoria() {
        Categoria CB=cbCategoria.getValue();
        return CB;
    }
}

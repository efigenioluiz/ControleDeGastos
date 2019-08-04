package sample.control;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.Categoria;
import sample.model.Despesa;
import sample.model.JDBC.JDBCCategoriaDAO;
import sample.model.JDBC.JDBCDespesaDAO;
import sample.model.JDBC.JDBCSubcategoriaDAO;
import sample.model.Subcategoria;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ControllerJanelaPainel {
    // dados Table view
    @FXML
    public TableView<Despesa> tvDespesas;
    @FXML
    public TableColumn<Despesa,String> tcNome;
    @FXML
    public TableColumn<Despesa,Double> tcValor;
    @FXML
    public TableColumn<Despesa,Categoria> tcCategoria;

    @FXML
    public TableColumn<Despesa,String> tcStatus;

    @FXML
    public Text txtNome;
    @FXML
    public AnchorPane TELA;
    public TableColumn tcSubcategoria;
    public Text tfData;


    public void  initialize(){
        txtNome.setText(String.valueOf(ControllerJanelaPrincipal.LOGADO.getNome()));
        tfData.setText(getPegaDataAtual());
        atualizaTV();
        ContextMenu cm = new ContextMenu();

        MenuItem mi1 = new MenuItem("Editar");
        cm.getItems().add(mi1);

        MenuItem mi2 = new MenuItem("Remover");
        cm.getItems().add(mi2);

        MenuItem mi3 = new MenuItem("Pagar");
        cm.getItems().add(mi3);

        tvDespesas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(t.getButton() == MouseButton.SECONDARY) {
                    cm.show(tvDespesas, t.getScreenX(), t.getScreenY());
                } else {
                    cm.hide();
                }
            }
        });

        mi1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Despesa d = tvDespesas.getSelectionModel().getSelectedItem();
                try {
                    System.out.println("vai dar update");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mi2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Despesa d = tvDespesas.getSelectionModel().getSelectedItem();
                System.out.println("vai remover");
            }
        });

        mi3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Despesa d = tvDespesas.getSelectionModel().getSelectedItem();
                try {
                    JDBCDespesaDAO.getInstance().pagar(ControllerJanelaPrincipal.LOGADO,d);
                    atualizaTV();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void sair(){
        tvDespesas.getItems().clear();
        mudaJanela("../view/JanelaPrincipal.fxml");
    }
    public String getPegaDataAtual() {
        Date dataa = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("dd-MM-yyyy");
        return formatarDate.format(dataa);
    }

    public void mudaJanela(String endereco){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(endereco));

                try {
                    Parent layoutJanela = loader.load();

                    Stage stage=(Stage)TELA.getScene().getWindow();

                    stage.setScene(new Scene(layoutJanela,600, 500));
                    stage.setResizable(false);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    private void cadastrarDespesas(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Cadastrar Despesas");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/JanelaCadastrarDespesas.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){
                ControllerJanelaCadastraDespesas control= fxmlLoader.getController();
                control.processResult();
                atualizaTV();
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (NullPointerException e ){
            mensagem(Alert.AlertType.ERROR,"Ops Campo vazio :/");
            cadastrarDespesas();
        } catch (Exception e) {
            e.printStackTrace();
            mensagem(Alert.AlertType.ERROR,"Valor Invalido, Tente Novamente!");
            cadastrarDespesas();
        }
    }
    @FXML
    private void cadastrarCategoria(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Cadastrar Categoria");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/JanelaCadastrarCategoria.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());


            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){
                ControllerJanelaCadastrarCategoria control= fxmlLoader.getController();

                if (control.processResult().isEmpty()){
                    mensagem(Alert.AlertType.ERROR,"Categoria Invalida");
                }else {
                    try{
                        Categoria c= new Categoria(control.processResult());
                        JDBCCategoriaDAO.getInstance().create(c);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void cadastrarSubcategoria(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Cadastrar SubCategoria");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/JanelaCadastrarSubcategoria.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){
                ControllerJanelaCadastrarSubcategoria control= fxmlLoader.getController();


                if (control.processResult().isEmpty() || control.processResult2() ==  null){
                    mensagem(Alert.AlertType.ERROR,"SubCategoria Invalida");
                }else {
                    try{
                        Subcategoria sc= new Subcategoria(control.processResult());
                        JDBCSubcategoriaDAO.getInstance().create(sc,control.processResult2());
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void visualizarDespesa(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Visualizar");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/JanelaVisualizarDespesas.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){

            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void visualizarDia(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Visualizar por Dia");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/JanelaVisualizarDia.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){

            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void visualizarData(){
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Visualizar por Data");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/JanelaVisualizarData.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){

            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    protected void mensagem(Alert.AlertType tipo, String mensagem){
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Alerta!!");
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
    public void atualizaTV(){
        tvDespesas.getItems().clear();

        tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeDespesa"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tcSubcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));

        tvDespesas.setItems(JDBCDespesaDAO.getInstance().listDespesa(ControllerJanelaPrincipal.LOGADO));
    }

    public void apagarDespesas(ActionEvent event) throws Exception {
        mensagem(Alert.AlertType.INFORMATION,"Despesas apagadas com Sucesso!");
        JDBCDespesaDAO.getInstance().deleteAll(ControllerJanelaPrincipal.LOGADO);
        atualizaTV();
    }

    public void pagarDespesa() {
        Dialog<ButtonType> dialog = new Dialog();
        dialog.setTitle("Pagar ");

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/JanelaPagarDespesas.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){
                atualizaTV();
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void grafico() {
        mudaJanela("../view/JanelaGrafico.fxml");
    }
}

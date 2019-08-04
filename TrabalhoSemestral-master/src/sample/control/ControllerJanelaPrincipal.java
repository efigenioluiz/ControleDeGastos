package sample.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import sample.model.JDBC.JDBCUsuarioDAO;
import sample.model.Usuario;

import java.io.IOException;
import java.util.Optional;

public class ControllerJanelaPrincipal {

    @FXML
    public GridPane TELA;

    @FXML
    private TextField login;

    @FXML
    private PasswordField senha;

    public static Usuario LOGADO;

    public void initialize(){

   }

   public void CadastrarUsuario(){

           Dialog<Pair<String, String>> dialog = new Dialog<>();
           dialog.setTitle("Cadastro");
           dialog.setHeaderText("Faça o seu Cadasto!\nTodos os Campos devem possuir no minimo 4 Caracteres!");

           ButtonType loginButtonType = new ButtonType("Cadastrar", ButtonBar.ButtonData.OK_DONE);
           dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

           GridPane grid = new GridPane();
           grid.setHgap(10);
           grid.setVgap(10);
           grid.setPadding(new Insets(20, 150, 10, 10));

           TextField username = new TextField();
           username.setPromptText("Username");
           PasswordField password = new PasswordField();
           password.setPromptText("Password");

           TextField nome=new TextField();
           nome.setPromptText("Nome");

       grid.add(new Label("Nome:"),0,0);
       grid.add(nome,0,1);
       grid.add(new Label("Login:"), 0, 2);
       grid.add(username, 0, 3);
       grid.add(new Label("Senha:"), 0, 4);
       grid.add(password, 0, 5);

           Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);

           username.textProperty().addListener((observable, oldValue, newValue) -> {
               loginButton.setDisable(newValue.trim().isEmpty());
           });

           dialog.getDialogPane().setContent(grid);

           Platform.runLater(() -> nome.requestFocus());
           
           dialog.setResultConverter(dialogButton -> {
               if (dialogButton == loginButtonType) {
                   return new Pair<>(username.getText(), password.getText());
               }
               return null;
           });

           Optional<Pair<String, String>> result = dialog.showAndWait();

           result.ifPresent(usernamePassword -> {
           Usuario u= new Usuario(nome.getText(),usernamePassword.getKey(),usernamePassword.getValue());

           try {
               if(JDBCUsuarioDAO.getInstance().verificaExistente(u) == false) {
                   if (u.getUsername().length() >= 4 && u.getSenha().length() >= 4 && u.getSenha().length() >= 4) {
                       JDBCUsuarioDAO.getInstance().create(u);
                       mensagem(Alert.AlertType.INFORMATION, "Cadastrado com Sucesso!");
                       login.requestFocus();
                   }else{
                       mensagem(Alert.AlertType.ERROR, "Nome ou Login ou sua senha está invalida");
                       CadastrarUsuario();
                   }
               }else {
                   mensagem(Alert.AlertType.ERROR, "Login já Cadastrado!");
                   CadastrarUsuario();
               }
               login.setText("");
               senha.setText("");
               login.requestFocus();
           } catch (Exception e) {
               e.printStackTrace();
           }
       });
   }
    protected void mensagem(Alert.AlertType tipo, String mensagem){
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Alerta!!");
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
   public void logar(){
        Usuario u= new Usuario();
        u.setUsername(login.getText());
        u.setSenha(senha.getText());

        if(JDBCUsuarioDAO.getInstance().verificaLogin(u)){

            LOGADO=JDBCUsuarioDAO.getInstance().retUsurario(u);

            mudaJanela("../view/JanelaPainel.fxml");
        }else {
            mensagem(Alert.AlertType.ERROR,"Login ou senha Invalido");
            //apos errar a senha or login, limpa os campos e altera o foco;
            login.setText("");
            senha.setText("");
            login.requestFocus();
        }
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

                    stage.setScene(new Scene(layoutJanela,600 , 500));
                    stage.setResizable(false);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    private void pegaTecla(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER){
            logar();
        }
    }
}

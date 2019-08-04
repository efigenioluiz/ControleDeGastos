package sample.control;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.Despesa;
import sample.model.JDBC.JDBCDespesaDAO;

import java.io.IOException;

public class ControllerJanelaGrafico {

    public AnchorPane TELA;
    public PieChart pcGrafico= new PieChart();

    public void initialize(){
        populaGraf();
    }
    public void voltar() {
        mudaJanela("../view/JanelaPainel.fxml");
    }
    public void populaGraf(){

        pcGrafico.getData().clear();
        for(Despesa d : JDBCDespesaDAO.getInstance().listGrafico(ControllerJanelaPrincipal.LOGADO)){
            pcGrafico.getData().addAll(new PieChart.Data(d.getNomeDespesa(),d.getValor()));
        }
        pcGrafico.setTitle("Despesas");
        pcGrafico.setLabelsVisible(true);
        pcGrafico.setPrefSize(360,400);

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

}


package sample.control;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.JDBC.JDBCDespesaDAO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ControllerJanelaVisualizarData {
    public TableView tvDespesasCat;
    public TableColumn tcNome;
    public TableColumn tcValor;
    public TableColumn tcData;
    public TableColumn tcCategoria;
    public DatePicker dpData;
    public TableColumn tcSubcategoria;

    public void ok(){

        LocalDate data = dpData.getValue();
        LocalDate dataAtual= LocalDate.parse(getPegaDataAtual());

        tvDespesasCat.getItems().clear();

        tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeDespesa"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcSubcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));


        if(dataAtual.isBefore(data) ){
            tvDespesasCat.setItems(JDBCDespesaDAO.getInstance().listDespesaDataBefore(data,ControllerJanelaPrincipal.LOGADO));
        }else{
            tvDespesasCat.setItems(JDBCDespesaDAO.getInstance().listDespesaDataAfter(data,ControllerJanelaPrincipal.LOGADO));
        }

    }
    public String getPegaDataAtual() {
        Date dataa = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatarDate.format(dataa);
    /*
        String xdata= dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(xdata);*/

    }
}

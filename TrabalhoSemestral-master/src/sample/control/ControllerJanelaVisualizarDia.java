package sample.control;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.JDBC.JDBCDespesaDAO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ControllerJanelaVisualizarDia {
    public TextField tfDia;
    public TableView tvDespesasCat;
    public TableColumn tcNome;
    public TableColumn tcValor;
    public TableColumn tcData;
    public TableColumn tcCategoria;
    public TableColumn tcSubcategoria;

    public void ok(){

        LocalDate data = LocalDate.parse(getPegaDataAtual(Integer.valueOf(tfDia.getText())));

        System.out.println(data);
        /*String xdata= data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.printf(xdata);*/

        tvDespesasCat.getItems().clear();

        tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeDespesa"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tcSubcategoria.setCellValueFactory(new PropertyValueFactory<>("subcategoria"));

        tvDespesasCat.setItems(JDBCDespesaDAO.getInstance().listDespesaDia(data,ControllerJanelaPrincipal.LOGADO));

        //fazer o metodo que retorna as despesas do tfdia + LocalDate.now();
    }
    public String getPegaDataAtual(int dia) {
        Date dataa = new Date(System.currentTimeMillis());
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
        dataa.setDate(dataa.getDate()+dia);
        return formatarDate.format(dataa);
    }
}

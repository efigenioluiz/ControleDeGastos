package sample.control;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import sample.model.Despesa;
import sample.model.JDBC.JDBCDespesaDAO;

public class ControllerJanelaPagarDespesas {


    public ComboBox<Despesa> cbDespesa= new ComboBox<>();

    public void initialize() {
        cbDespesa.getItems().addAll(JDBCDespesaDAO.getInstance().listDespesaPendete(ControllerJanelaPrincipal.LOGADO));
    }


    public void pagar() {
        try {
            JDBCDespesaDAO.getInstance().pagar(ControllerJanelaPrincipal.LOGADO,cbDespesa.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(cbDespesa.getValue().getId());
    }
}

package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Despesa {

    public int id;
    public SimpleStringProperty nomeDespesa;
    public Double valor;
    public SimpleStringProperty data;
    public Categoria categoria;
    public Subcategoria subcategoria;
    public boolean status;
    public SimpleStringProperty estado;
    public SimpleStringProperty dataTV;

    public Despesa(String nomeDespesa, Double valor, String data, Categoria categoria,boolean status,Subcategoria sb) {
        this.nomeDespesa = new SimpleStringProperty(nomeDespesa);
        this.valor = valor;
        this.data = new SimpleStringProperty(data);
        this.categoria = categoria;
        this.subcategoria = sb;
        this.status=status;
        this.estado= new SimpleStringProperty(estadoTB(this.status));
    }

    public Despesa(int id,String nomeDespesa, Double valor, String data, Categoria categoria,boolean status,Subcategoria sb) {
        this.id=id;
        this.nomeDespesa = new SimpleStringProperty(nomeDespesa);
        this.valor = valor;
        this.data = new SimpleStringProperty(data);
        this.categoria = categoria;
        this.subcategoria = sb;
        this.status=status;
        this.estado= new SimpleStringProperty(estadoTB(this.status));
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Despesa() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.setValue(data);
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNomeDespesa() {
        return nomeDespesa.get();
    }

    public SimpleStringProperty nomeDespesaProperty() {
        return nomeDespesa;
    }

    public void setNomeDespesa(String nomeDespesa) {
        this.nomeDespesa.set(nomeDespesa);
    }

    public String getEstado() {
        return estado.get();
    }

    public SimpleStringProperty estadoProperty() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado.set(estado);
    }

    @Override
    public String toString() {
        return nomeDespesa.get();
    }

    public String estadoTB(boolean status){
        if(status){
            return  "Pago";
        }else {
            return "Pendente";
        }
    }
}

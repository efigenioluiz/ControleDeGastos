package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Categoria {

    private int id;
    private SimpleStringProperty nome;


    public Categoria(String nome) {
        this.nome =new SimpleStringProperty(nome);
    }

    public Categoria() {
    }

    @Override
    public String toString() {
        return nome.getValue();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome.getValue();
    }

    public void setNome(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }
}

package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Subcategoria {

    private int id;
    private SimpleStringProperty nome;
    private SimpleIntegerProperty fk_categoria;


    @Override
    public String toString() {
        return nome.get();
    }

    public Subcategoria(String nome) {
        this.nome= new SimpleStringProperty(nome);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public int getFk_categoria() {
        return fk_categoria.get();
    }

    public SimpleIntegerProperty fk_categoriaProperty() {
        return fk_categoria;
    }

    public void setFk_categoria(int fk_categoria) {
        this.fk_categoria.set(fk_categoria);
    }
}

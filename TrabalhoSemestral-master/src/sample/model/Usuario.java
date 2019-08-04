package sample.model;

import javafx.collections.ObservableList;

public class Usuario {

    private int id;
    private String nome;
    private String username;
    private String senha;
    private ObservableList<Despesa> despesas;

    public Usuario(String nome, String username, String senha) {
        this.nome=nome;
        this.username = username;
        this.senha = senha;
    }

    public Usuario() {
    }

/*    public Usuario(String nome, String username, String senha, ObservableList<Lancamento> despesas) {
        this.nome=nome;
        this.username = username;
        this.senha = senha;
        this.despesas = despesas;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ObservableList<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(ObservableList<Despesa> despesas) {
        this.despesas = despesas;
    }
}

package sample.model.interfaces;

import javafx.collections.ObservableList;
import sample.model.Categoria;
import sample.model.Despesa;
import sample.model.Usuario;

import java.util.List;

public interface CategoriaDAO {

    public void create(Categoria c) throws Exception;
    public List<Categoria> list();
    public Categoria search(int id) throws Exception;
    public void update(Categoria c) throws Exception;
    public void delete(Categoria c) throws Exception;
}

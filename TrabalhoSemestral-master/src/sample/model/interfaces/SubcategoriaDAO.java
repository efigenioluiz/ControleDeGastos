package sample.model.interfaces;


import sample.model.Categoria;
import sample.model.Subcategoria;

import java.util.List;

public interface SubcategoriaDAO {

    public void create(Subcategoria c, Categoria ca) throws Exception;
    public List<Subcategoria> list();
    public Subcategoria search(int id) throws Exception;
    public void update(Subcategoria c) throws Exception;
    public void delete(Subcategoria c) throws Exception;
}

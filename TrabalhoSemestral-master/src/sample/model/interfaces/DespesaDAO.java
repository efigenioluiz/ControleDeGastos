package sample.model.interfaces;


import sample.model.Despesa;

import java.util.List;

public interface DespesaDAO {

    void create(Despesa d, int fk_categoria, int fk_subcategoria) throws Exception;

    public List<Despesa> list();
    public Despesa search(int id) throws Exception;
    public void update(Despesa d) throws Exception;
    public void delete(Despesa d) throws Exception;
}

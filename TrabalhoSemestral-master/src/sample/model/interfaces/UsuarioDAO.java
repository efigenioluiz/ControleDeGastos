package sample.model.interfaces;

import sample.model.Usuario;

import java.util.List;

public interface UsuarioDAO {

    public void create(Usuario u) throws Exception;
    public List<Usuario> list();
    public Usuario search(int id) throws Exception;
    public void update(Usuario u) throws Exception;
    public void delete(Usuario u) throws Exception;
}

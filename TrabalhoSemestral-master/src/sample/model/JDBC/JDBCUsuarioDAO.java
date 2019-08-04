package sample.model.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.FabricaConexao;
import sample.model.Usuario;
import sample.model.interfaces.UsuarioDAO;

import java.sql.*;

public class JDBCUsuarioDAO implements UsuarioDAO {


   private static JDBCUsuarioDAO instance;
    private ObservableList<Usuario> lista;

    private JDBCUsuarioDAO(){
        lista = FXCollections.observableArrayList();
    }

    public static JDBCUsuarioDAO getInstance(){
        if(instance==null){
            instance = new JDBCUsuarioDAO();
        }
        return instance;
    }


    @Override
    public void create(Usuario u) throws Exception {


            String sql = "insert into usuarios(nome,login,senha) values ('"+u.getNome()+"','"+u.getUsername()+"','"+u.getSenha()+"');";

            Connection c = FabricaConexao.getConnection();
            PreparedStatement pstm = c.prepareStatement(sql);

            pstm.execute();
            pstm.close();
            c.close();
    }


    private Usuario montaUsuario(ResultSet rs)throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String login=rs.getString("login");
        String senha=rs.getString("senha");

        Usuario u = new Usuario(nome,login,senha);
        u.setId(id);
        return u;
    }


    @Override
    public ObservableList<Usuario> list() {

        lista.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select * from usuarios");

            while (rs.next()){
                Usuario u = montaUsuario(rs);
                lista.add(u);
            }
            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Usuario search(int id) throws Exception {
        Connection c = FabricaConexao.getConnection();
        String sql = "select * from usuarios where id=?";

        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        Usuario u = null;
        while(rs.next()){
            u = montaUsuario(rs);
        }
        rs.close();
        pstm.close();
        c.close();
        return u;
    }
    @Override
    public void update(Usuario u) throws Exception {
/*       String sql = "update jogador "+
                "where id="+j.getId()+";";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement pstm = c.prepareStatement(sql);


        pstm.execute();
        pstm.close();
        c.close();*/
    }
    @Override
    public void delete(Usuario u) throws Exception {
        String sql = "DELETE from usuarios "+
                "where id="+u.getId()+";";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement pstm = c.prepareStatement(sql);


        pstm.execute();
        pstm.close();
        c.close();
    }
    public boolean verificaLogin(Usuario u){
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from usuarios where login='"+u.getUsername()+"'and senha='"+u.getSenha()+"';");

            Usuario usuario = montaUsuario(rs);

            rs.close();
            stm.close();
            c.close();
            if(usuario != null){
                return true;
            }else {
                return  false;
            }
        }catch (SQLException e){
            e.getMessage();
        }

        return false;

    }
    public Usuario retUsurario(Usuario u){
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from usuarios where login='"+u.getUsername()+"'and senha='"+u.getSenha()+"';");

            Usuario usuario = montaUsuario(rs);
            rs.close();
            stm.close();
            c.close();
            return  usuario;
        }catch (SQLException e){
            return u;
        }
    }

    public boolean verificaExistente(Usuario u) {
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from usuarios where login='"+u.getUsername()+"';");

            Usuario us= montaUsuario(rs);
            if(u.getUsername().equals(us.getUsername())){
                return  true;
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.getMessage();
        }

        return false;
    }
}

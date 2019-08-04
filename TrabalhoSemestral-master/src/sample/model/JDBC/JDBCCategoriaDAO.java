package sample.model.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.control.ControllerJanelaPrincipal;
import sample.model.Categoria;
import sample.model.FabricaConexao;
import sample.model.Usuario;
import sample.model.interfaces.CategoriaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCCategoriaDAO implements CategoriaDAO {

    private static JDBCCategoriaDAO instance;
    private ObservableList<Categoria> lista;

    private JDBCCategoriaDAO(){
        lista = FXCollections.observableArrayList();
    }

    public static JDBCCategoriaDAO getInstance(){
        if(instance==null){
            instance = new JDBCCategoriaDAO();
        }
        return instance;
    }
    @Override
    public void create(Categoria cg) throws Exception {
        String sql = "insert into categorias(nome,fk_usuario) values ('"+cg.getNome()+"','"+ControllerJanelaPrincipal.LOGADO.getId() +"');";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement pstm = c.prepareStatement(sql);

        pstm.execute();
        pstm.close();
        c.close();
    }

    @Override
    public List<Categoria> list() {
        return null;
    }

    private Categoria montaCategoria(ResultSet rs)throws SQLException {
        int id = rs.getInt("id_categoria");
        String nome = rs.getString("nome");

        Categoria u = new Categoria(nome);
        u.setId(id);

        return u;
    }
    @Override
    public Categoria search(int id) throws Exception {
        Connection c = FabricaConexao.getConnection();
        String sql = "select * from categorias where id_categoria=?";

        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        Categoria ca = null;
        while(rs.next()){
            ca = montaCategoria(rs);
        }
        rs.close();
        pstm.close();
        c.close();
        return ca;
    }
    public ArrayList<Categoria> listCategoria(Usuario u) {
        ArrayList<Categoria> jo= new ArrayList<>();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from categorias where fk_usuario ='"+u.getId()+"';");

            while (rs.next()){
                jo.add(montaCategoria(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return jo;
    }
    @Override
    public void update(Categoria c) throws Exception {

    }

    @Override
    public void delete(Categoria c) throws Exception {

    }
}

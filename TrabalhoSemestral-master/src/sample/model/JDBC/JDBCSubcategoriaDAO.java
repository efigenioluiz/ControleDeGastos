package sample.model.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.control.ControllerJanelaPrincipal;
import sample.model.Categoria;
import sample.model.FabricaConexao;
import sample.model.Subcategoria;
import sample.model.Usuario;
import sample.model.interfaces.SubcategoriaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSubcategoriaDAO implements SubcategoriaDAO {

    private static JDBCSubcategoriaDAO instance;
    private ObservableList<Subcategoria> lista;

    private JDBCSubcategoriaDAO(){
        lista = FXCollections.observableArrayList();
    }

    public static JDBCSubcategoriaDAO getInstance(){
        if(instance==null){
            instance = new JDBCSubcategoriaDAO();
        }
        return instance;
    }

    @Override
    public void create(Subcategoria sc, Categoria ca) throws Exception {
        String sql = "insert into subcategorias(nome_subcategoria,fk_usuario,fk_categoria) values ('"+sc.getNome()+"','"+ControllerJanelaPrincipal.LOGADO.getId() +"','"+ca.getId()+"');";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement pstm = c.prepareStatement(sql);

        pstm.execute();
        pstm.close();
        c.close();
    }

    @Override
    public List<Subcategoria> list() {
        return null;
    }

    private Subcategoria montaSubcategoria(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_subcategoria");
        String nome = rs.getString("nome_subcategoria");

        Subcategoria u = new Subcategoria(nome);
        u.setId(id);

        return u;
    }

    @Override
    public Subcategoria search(int id) throws Exception {
        Connection c = FabricaConexao.getConnection();
        String sql = "select * from subcategorias where id_subcategoria=?";

        PreparedStatement pstm = c.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        Subcategoria ca = null;
        while(rs.next()){
            ca = montaSubcategoria(rs);
        }
        rs.close();
        pstm.close();
        c.close();
        return ca;
    }
    public ArrayList<Subcategoria> listSubcategoria(Usuario u,Categoria ca) {
        ArrayList<Subcategoria> jo= new ArrayList<>();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from subcategorias where fk_usuario ='"+u.getId()+"' and fk_categoria='"+ca.getId()+"';");

            while (rs.next()){
                jo.add(montaSubcategoria(rs));
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
    public void update(Subcategoria c) throws Exception {

    }

    @Override
    public void delete(Subcategoria c) throws Exception {

    }
}

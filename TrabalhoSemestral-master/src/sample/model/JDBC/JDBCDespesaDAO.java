package sample.model.JDBC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.control.ControllerJanelaPrincipal;
import sample.model.*;
import sample.model.interfaces.DespesaDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JDBCDespesaDAO implements DespesaDAO {

    private static JDBCDespesaDAO instance;
    private ObservableList<Despesa> lista;

    private JDBCDespesaDAO(){
        lista = FXCollections.observableArrayList();
    }

    public static JDBCDespesaDAO getInstance(){
        if(instance==null){
            instance = new JDBCDespesaDAO();
        }
        return instance;
    }
    @Override
    public void create(Despesa d,int fk_categoria,int fk_subCategoria) throws Exception {
        String sql = "insert into despesas(nomeDespesa,valor,data,fk_usuario,fk_categoria,status,fk_subcategoria) values ('"+d.getNomeDespesa()+"',"+d.getValor()+",'"+d.getData()+"','"+ControllerJanelaPrincipal.LOGADO.getId() +"','"+fk_categoria+"',"+false+",'"+fk_subCategoria+"');";
        Connection c = FabricaConexao.getConnection();
        PreparedStatement pstm = c.prepareStatement(sql);

        pstm.execute();
        pstm.close();
        c.close();

    }

    @Override
    public List<Despesa> list() {
        return null;
    }
    private Despesa montaDespesa(ResultSet rs) throws Exception {

        int id = rs.getInt("id_Despesa");
        String nome = rs.getString("nomeDespesa");
        Double valor = rs.getDouble("valor");
        String data=rs.getString("data");
        int fk_usuario=rs.getInt("fk_usuario");
        int fk_categoria=rs.getInt("fk_categoria");
        boolean status=rs.getBoolean("status");
        int fk_subcategoria=rs.getInt("fk_subcategoria");

        Categoria ca= JDBCCategoriaDAO.getInstance().search(fk_categoria);
        Subcategoria sb= JDBCSubcategoriaDAO.getInstance().search(fk_subcategoria);

        Despesa d = new Despesa(id,nome,valor,data,ca,status,sb);

        return d;
    }
    public ObservableList<Despesa> listDespesa(Usuario u) {
        lista.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from despesas where fk_usuario ='"+u.getId()+"';");
            while (rs.next()){
                lista.add(montaDespesa(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {

        }
        return lista;
    }
    public ObservableList<Despesa> listDespesaCategoria(Categoria categoria, Usuario u) {
        ObservableList<Despesa> lista=FXCollections.observableArrayList();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from despesas where fk_categoria ='"+categoria.getId()+"' and fk_usuario='"+u.getId()+"';");
            while (rs.next()){
                lista.add(montaDespesa(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {

        }
        return lista;
    }
    public ObservableList<Despesa> listDespesaDataBefore(LocalDate data, Usuario u) {
        ObservableList<Despesa> lista=FXCollections.observableArrayList();
        try {
            Date dat = new Date(System.currentTimeMillis());
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from despesas where data between '"+dat+"' and '"+data+"' and fk_usuario='"+u.getId()+"';");
            while (rs.next()){
                lista.add(montaDespesa(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {

        }
        return lista;
    }
    public ObservableList<Despesa> listDespesaDataAfter(LocalDate data, Usuario u) {
        ObservableList<Despesa> lista=FXCollections.observableArrayList();
        try {
            Date dat = new Date(System.currentTimeMillis());
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from despesas where data between '"+data+"' and '"+dat+"' and fk_usuario='"+u.getId()+"';");
            while (rs.next()){
                lista.add(montaDespesa(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {

        }
        return lista;
    }
    public ObservableList<Despesa> listDespesaDia(LocalDate data, Usuario u) {
        ObservableList<Despesa> lista=FXCollections.observableArrayList();
        try {
            Date dat = new Date(System.currentTimeMillis());
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from despesas where data between '"+dat+"' and '"+data+"' and fk_usuario='"+u.getId()+"';");
            while (rs.next()){
                lista.add(montaDespesa(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {

        }
        return lista;
    }
    @Override
    public void update(Despesa d) throws Exception {
/*        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("update despesas set status=true  where id_despesa="+d.getId()+";");
            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.getMessage();
        }*/
    }
    public void pagar(Usuario u, Despesa d) throws Exception {
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("update despesas set status=true  where id_despesa="+d.getId()+" and fk_usuario='"+u.getId()+"';");
            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.getMessage();
        }
    }

    @Override
    public Despesa search(int id) throws Exception {
        return null;
    }

    @Override
    public void delete(Despesa d) throws Exception {

    }

    public void deleteAll(Usuario u) throws Exception {
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("delete from despesas where fk_usuario='"+u.getId()+"'");
            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.getMessage();
        }

    }

    public ObservableList<Despesa> listDespesaPendete(Usuario u) {
            ObservableList<Despesa> pendente = FXCollections.observableArrayList();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from despesas where fk_usuario ='"+u.getId()+"' and status="+false+";");
            while (rs.next()){
                pendente.add(montaDespesa(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {

        }
        return pendente;
    }

    public ObservableList<Despesa> listGrafico(Usuario u) {
        ObservableList<Despesa> dado = FXCollections.observableArrayList();
        dado.clear();
        try {
            Connection c = FabricaConexao.getConnection();
            Statement stm = c.createStatement();
            ResultSet rs = stm
                    .executeQuery("select *from despesas where fk_usuario ='"+u.getId()+"';");
            while (rs.next()){
                dado.add(montaDespesa(rs));
            }

            rs.close();
            stm.close();
            c.close();
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e) {

        }
        return dado;
    }
}

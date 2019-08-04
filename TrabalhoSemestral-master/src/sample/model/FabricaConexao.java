package sample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class FabricaConexao {

    private static Connection[] pool;
    public static SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");
    //public static DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    private static String CONNECTION_STR = "jdbc:sqlite:bancotrab.sqlite";
    private static int MAX_CONNECTIONS=6;

    static {
        pool = new Connection[MAX_CONNECTIONS];
    }

    public static Connection getConnection() throws SQLException{

        for(int i=0;i<pool.length;i++){
            if((pool[i]==null) || (pool[i].isClosed())){
                pool[i] = DriverManager.getConnection(CONNECTION_STR);
                return pool[i];
            }
        }
        throw new SQLException("Muitas conexões abertas!!!");
    }

}

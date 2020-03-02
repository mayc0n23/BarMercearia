package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoPostgres {
	private String usuario = "postgres";
    private String pass = "maycon";
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String driver = "postgresql.Driver";
    private Connection getConexao;
    private volatile static ConexaoPostgres instance;
     
    private ConexaoPostgres() {
         
    }
     
    /*
     * Padrao Singleton 
     */
     
    public static ConexaoPostgres getInstance() { 
        if(instance == null) {
             synchronized (ConexaoPostgres.class){
                 if( instance == null ) {
                     instance = new ConexaoPostgres();
                 }
             }
        }    
        return instance;
    }
     
    /*
     * Metodo para conectar-se com o banco
     */
    public Connection conectar(){
         
        try {
            /// seta o driver da conexao
            System.setProperty("org.Drives", driver);
            // passa os dados e recebe a conexao
            getConexao = DriverManager.getConnection(url, usuario, pass);
            return getConexao;
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoPostgres.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return null;
    }
     
     
    /*
     * Metodo desconectar-se do banco 
     * fechar a conexao
     */
    public void desconectar(){
        try {
            getConexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoPostgres.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() {
    	return getConexao;
    }
}
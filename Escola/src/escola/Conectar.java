package escola;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conectar {
    public Connection conectar = null;  // Conexao
    public PreparedStatement ps = null; // Operação
    public ResultSet rs = null;         // Dados
    
    public Conectar(String ip, String username, String password) {
        //Conectar
        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(ip, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("ERRO Banco de Dados");
            System.exit(-1); //Fecha o programa
        }
    }
}

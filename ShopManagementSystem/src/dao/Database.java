package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Classe che provvede alla connessione con il database
 * @author Antonio Di Nuzzo
 *
 */
public class Database {

    private static Database instance;
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String username = "postgres";
    private String password = "admin";
	public  static Connection con ;
	
	private Database()  {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(null,"Database Connection Creation Failed : " + ex.getMessage(),"Error", 0);
        } catch(SQLException sqle) {
        	
        }
    }
	
	public Connection getConnection() {
        return connection;
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        } else if (instance.getConnection().isClosed()) {
            instance = new Database();
        }

        return instance;
    }
}

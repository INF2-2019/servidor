package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost/";
	private final static String USUARIO = "root";
	private final static String SENHA = "123456";
	
	public static Connection getConnection(String database) {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL + database, USUARIO, SENHA);            
		} catch(ClassNotFoundException e) {
			System.err.println("Driver não encontrado: " + e);
			return null;
		} catch(SQLException e) {
			System.err.println("Falha ao conectar ao banco de dados: " + e);
			return null;
		}
	}

	public static Connection getDiario() {
		return getConnection("diario");
	}

	public static Connection getBiblioteca() {
		return getConnection("biblioteca");
	}

}

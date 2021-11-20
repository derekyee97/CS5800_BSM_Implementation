package model.dataccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory 
{
	final String URL = "jdbc:postgresql://localhost:5432/authentication";

	final String USER = "postgres";

	final String PWD = "@Test123";
	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(URL, USER, PWD);
	}
}

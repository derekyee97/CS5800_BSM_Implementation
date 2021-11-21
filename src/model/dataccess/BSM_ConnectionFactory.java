package model.dataccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BSM_ConnectionFactory 
{
	
	final String URL = "jdbc:postgresql://localhost:5432/BSM";  //edit last part with your database name (if kept everythig else default)

	final String USER = "postgres";

	final String PWD = "@Test123";
	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(URL, USER, PWD);
	}
	
}

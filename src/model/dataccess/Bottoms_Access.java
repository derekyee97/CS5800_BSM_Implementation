package model.dataccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.entities.Item;

public class Bottoms_Access {
	final String USER = "postgres";
	final String PASSWORD = "Winter005#"; //Make sure to change password
	
	public boolean bottoms_retrieval(Item item) throws ClassNotFoundException {
		boolean res = false;
		String bottoms_retrieval_vals = "Select * FROM items WHERE items.type='bottoms';";
		
		Class.forName("org.postgresql.Driver");
		try{
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BSM",USER, PASSWORD);
			PreparedStatement ps = con.prepareStatement(bottoms_retrieval_vals);
			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			res = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;	
	}
}

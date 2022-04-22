package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnect {

	private static String url = "jdbc:mysql://localhost:3306/electronic_grid";
	private static String userName = "ElectroGridUser";
	private static String password = "1234";
	private static Connection con;
	
	//A common method to connect to the DB
	public Connection connect()
	{
		Connection con = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection(url, userName, password);
			
			System.out.print("Successfully connected");
			
		}
		catch (Exception e) {
			System.out.println("Database connection is not success!!!");
		}
		
		return con;
}
}

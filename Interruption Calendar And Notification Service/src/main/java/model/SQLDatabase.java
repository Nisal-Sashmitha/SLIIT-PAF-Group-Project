package model;

import java.sql.*;

public class SQLDatabase {
	Connection con;
	
	public SQLDatabase() {
		try
		 {
		 Class.forName("com.mysql.jdbc.Driver");

		 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
		 }
		 catch (Exception e)
		 {e.printStackTrace();}
	}
	
	public Connection getConnection() {
		return con;
	}
}

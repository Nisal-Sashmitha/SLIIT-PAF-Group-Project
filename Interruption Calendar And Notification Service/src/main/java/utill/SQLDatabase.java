package utill;

import java.sql.*;

public class SQLDatabase {
	Connection con;
	
	public SQLDatabase() {
		try
		 {
		 Class.forName("com.mysql.jdbc.Driver");

		 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electronicgriddb", "ElectroGridUser", "1234");
		 }
		 catch (Exception e)
		 {e.printStackTrace();}
	}
	
	public Connection getConnection() {
		return con;
	}
}

package model;
import java.sql.*;

public class Bill {
	
	//connection with the mysql workbench
	public Connection connect(){
		
		Connection con = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_db",
			"root", "");
			
			//For testing
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		return con;
	}
	
	//retrive bill details
	public String getBillDetails() {
		
		String output=null;
		
		Connection con = connect();
		
		if(con==null) {
			output="DB connection error";
		}
		
		try {
			// Prepare the html table to be displayed
						output = "<table border='1'>"
						+ "<tr><th>Account No</th><th>credit balance</th><th>MeterReadCurrentMonth</th>"
						+ "<th>MeterReadingLastMonth</th><th>status</th><th>year</th><th>month</th></tr>";
						
						String query = "select * from Bill";
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						
						// iterate through the rows in the result set
						while (rs.next())
						{
							int billID = rs.getInt("billID");
							String AccNo = Integer.toString(rs.getInt("AccNo"));
							float creditBalance = rs.getFloat("creditBalance");
							int lastMeterReadCurrentMonth = rs.getInt("lastMeterReadingsCurrentMonth");
							int lastMeterReadingLastMonth = rs.getInt("lastMeterReadingsPreviousMonth");
							String status = rs.getString("status");
							int year = rs.getInt("year");
							int month = rs.getInt("month");
							
							// Add a row into the html table
							output += "<tr><td>" + AccNo + "</td>";
							output += "<td>" + creditBalance + "</td>";
							output += "<td>" + lastMeterReadCurrentMonth + "</td>";
							output += "<td>" + lastMeterReadingLastMonth + "</td>";
							output += "<td>" + status + "</td>";
							output += "<td>" + year + "</td>";
							output += "<td>" + month + "</td>";
							
							// buttons
							output += "<td><input name='btnUpdate' "
							+ " type='button' value='Update'></td>"
							+ "<td><form method='post' action='Items.jsp'>"
							+ "<input name='btnRemove' "
							+ " type='submit' value='Remove'>"
							+ "<input name='itemID' type='hidden' "
							+ " value='" + billID + "'>" + "</form></td></tr>";
						}
						
						con.close();
						
						output += "</table>";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}
	
	public String InsertBill(String AccNo,float creditBalance,int lastMeterReadCurrentMonth,int lastMeterReadingLastMonth,String status,int year, int month) {
		
		String result=null;
		Connection con = connect();
		
		if(con==null) {
			result="DB connection error";
		}
		
		String query= "insert into Bill(`BillID`,`AccNo`,`creditBalance`,`lastMeterReadingsPreviousMonth`,`lastMeterReadingsCurrentMonth`,`status`,`year`,`month`)" + "values(?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//here the parameters values of insert method assigned to 1st placeholder, 2nd placeholder,.... in the prepared statement.
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, AccNo);
			preparedStmt.setFloat(3, creditBalance);
			preparedStmt.setInt(4,lastMeterReadCurrentMonth);
			preparedStmt.setInt(5,lastMeterReadingLastMonth);
			preparedStmt.setString(6, status);
			preparedStmt.setInt(7, year);
			preparedStmt.setInt(8, month);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}

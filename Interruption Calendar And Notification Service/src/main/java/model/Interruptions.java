package model;

import java.sql.*;

public class Interruptions {
	private int interruptionID;
	private String date;
	private String startTime;
	private String endTime;
	private String areaID;
	
	
	public String newInterruption(int interruptionID, String date, String startTime, String endTime, String areaID) {
		String output = "";
		String query = "insert into interruption('interruptionID', 'date', 'startTime', 'endTime', 'areaID' )"
		+"values(?,?,?,?,?) ";
		
		
		try {
			SQLDatabase db = new SQLDatabase();
			Connection con = db.getConnection();
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, startTime);
			preparedStmt.setString(4, endTime);
			preparedStmt.setString(5, areaID);
			
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
			
			
		}catch(Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
}

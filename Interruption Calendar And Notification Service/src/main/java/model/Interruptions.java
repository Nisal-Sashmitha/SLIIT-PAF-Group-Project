package model;

import java.sql.*;

public class Interruptions {
	private int interruptionID;
	private String date;
	private String startTime;
	private String endTime;
	private String areaID;
	
	
	public String newInterruption( String date, String startTime, String endTime, String areaID) {
		String output = "";
		String query = "INSERT INTO `electronicgriddb`.`interruption` (`date`, `startTIme`, `endTime`, `areaID`) VALUES (?, ?, ?, ?); ";
		
		
		try {
			SQLDatabase db = new SQLDatabase();
			Connection con = db.getConnection();
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			
			preparedStmt.setString(1, date);
			preparedStmt.setString(2, startTime);
			preparedStmt.setString(3, endTime);
			preparedStmt.setString(4, areaID);
			
			preparedStmt.execute();
			
			con.close();
			output = "Inserted successfully";
			
			
		}catch(Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public void interruptionsOfTheConsumer(String NIC) {}
	
	public void viewAllInterruptionsOfADay() {}
	
	public String editInterruptions(String interruptionID, String date, String startTime, String endTime,String areaID) {
		String output = "";
		 try
		 {
			 SQLDatabase db = new SQLDatabase();
			 Connection con = db.getConnection();
			 if (con == null)
			 {return "Error while connecting to the database for updating."; }
			 
			 // create a prepared statement
			 String query = "UPDATE `electronicgriddb`.`interruption` SET `date` = ?, `startTIme` = ?, `endTime` = ?, `areaID` = ? WHERE (`interruptionID` = ?);";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setString(1, date);
			 preparedStmt.setString(2, startTime);
			 preparedStmt.setString(3, endTime);
			 preparedStmt.setString(4, areaID);
			 preparedStmt.setString(5, interruptionID);
        	// execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Updated successfully";
		 }
		 catch (Exception e)
		 {
			 output = "Error while updating the item.";
			 System.err.println(e.getMessage());
		 }
		 return output; 

	}
	
	public String deleteInterruption(String interruptionID) {
		
		String output = "";
		try
		 {
			 SQLDatabase db = new SQLDatabase();
			 Connection con = db.getConnection();
			 if (con == null)
			 {return "Error while connecting to the database for deleting."; }
			 // create a prepared statement
			 String query = "delete from `interruption` where `interruptionID`=?";
			 
			
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setString(1, interruptionID);
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Deleted successfully";
		 }
		 catch (Exception e)
		 {
			 output = "Error while deleting the item.";
			 System.err.println(e.getMessage());
		 }
		 return output; 
		
	}
	
	public void viewNotifications() {}
	
	
}

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import util.DBconnect;

public class complaint {

	//creating an object to connect to DBconnect
	DBconnect dbconn = new DBconnect();
	
	
	
	//Insert 
	public String Createcomplaint(String Account_no, String complaint_T, String contact_no, String message) { 
		
		 String output = ""; 
		 try
		 { 
			 
		// create connection object & call the connection method	 
		 Connection con = dbconn.connect(); 
		 
		 if (con == null) {
			 return "Error while connecting to the database for inserting complaint."; 
		} 
		 
		 String query = " insert into complaints(`complaintID`,`AccNo`,`complaintType`,`contactNo`,`message`)"
					+ " values (?, ?, ?, ?,?)";
		 // create a prepared statement
	
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
	
		 // binding values
		 preparedStmt.setInt(1, 0); 
		 preparedStmt.setString(2, Account_no); 
		 preparedStmt.setString(3, complaint_T); 
		 preparedStmt.setString(4, contact_no); 
		 preparedStmt.setString(5, message); 
		 
	
		 // execute the statement
		 preparedStmt.execute(); 
		 
		 // close the connection
		 con.close(); 
		 output = "Complaint Created Successfully."; 
		 }
		 catch (Exception e) { 
			 output = "Error while inserting complaint."; 
			 System.err.println(e.getMessage()); 
			 
		 } 
	 
	 return output;
	 
	 }
	
   //read all pending complaints
		public String readpendingcomplaints()
			{
			String output = "";
			try
			{
			Connection con = dbconn.connect(); 
					if (con == null)
					{
						return "Error while connecting to the database for reading."; 
					}
						// Prepare the html table to be displayed
						output = "<table border='1'><tr><th>Complaint type</th><th>contact number</th>" +
						"<th>Message</th>" +
						"<th>Date</th>" +
						"<th>Reply</th>" +
						"<th>Status</th>" +
						"<th>Update</th><th>Remove</th></tr>";
						
						String query = "select * from complaints where status= 'pending' " ;
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
						// iterate through the rows in the result set
						while (rs.next())
						{
							String complaintID = Integer.toString(rs.getInt("complaintID"));
							String accno = rs.getString("AccNo");
							String complainttype = rs.getString("complaintType");
							String mobile = rs.getString("contactNo");
							String cmessage =  rs.getString("message");
							String datentime = rs.getString("date");
							String reply = rs.getString("replyMessage");
							String status = rs.getString("status");
							
							
							// Add into the html table
							output += "<tr><td>" + complainttype + "</td>";
							output += "<td>" + mobile + "</td>";
							output += "<td>" + cmessage + "</td>";
							output += "<td>" + datentime + "</td>";
							output += "<td>" + reply + "</td>";
							output += "<td>" + status + "</td>";
							// buttons
							output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
							+ "<td><form method='post' action='items.jsp'>"
							+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							+ "<input name='itemID' type='hidden' value='" + complaintID
							+ "'>" + "</form></td></tr>";
						}
						con.close();
						// Complete the html table
						output += "</table>";
					}
			catch (Exception e)
			{
				output = "Error while reading the items.";
				System.err.println(e.getMessage());
			}
				return output;
			}
	
	
	//read all complaints
		public String readcomplaints()
		{
		String output = "";
		try
		{
		Connection con = dbconn.connect(); 
				if (con == null)
				{
					return "Error while connecting to the database for reading."; 
				}
					// Prepare the html table to be displayed
					output = "<table border='1'><tr><th>complaint type</th><th>contact number</th>" +
					"<th>message</th>" +
					"<th>Date</th>" +
					"<th>Reply</th>" +
					"<th>Status</th>" +
					"<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from complaints";
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					// iterate through the rows in the result set
					while (rs.next())
					{
						String complaintID = Integer.toString(rs.getInt("complaintID"));
						String accno = rs.getString("AccNo");
						String complainttype = rs.getString("complaintType");
						String mobile = rs.getString("contactNo");
						String cmessage =  rs.getString("message");
						String datentime = rs.getString("date");
						String reply = rs.getString("replyMessage");
						String status = rs.getString("status");
						
						
						// Add into the html table
						output += "<tr><td>" + complainttype + "</td>";
						output += "<td>" + mobile + "</td>";
						output += "<td>" + cmessage + "</td>";
						output += "<td>" + datentime + "</td>";
						output += "<td>" + reply + "</td>";
						output += "<td>" + status + "</td>";
						// buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + complaintID
						+ "'>" + "</form></td></tr>";
					}
					con.close();
					// Complete the html table
					output += "</table>";
				}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
			return output;
		}
		
		
		
		
	
	//update
	public String updateComplaint(String ID,String c_reply,String C_status)
	
	{
	String output = "";
	try
	{
		Connection con = dbconn.connect(); 
		if (con == null)
		{
			return "Error while connecting to the database for updating.";
		}
		// create a prepared statement
		String query = "UPDATE complaints SET replyMessage=?,status=? WHERE complaintID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setString(1, c_reply);
			preparedStmt.setString(2, C_status);
			preparedStmt.setInt(3, Integer.parseInt(ID));
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
	
	
	// Delete Operation
	public String deletecomplaint(String complaintID)
	{
		String output = "";
	try
	{
	Connection con = dbconn.connect(); 
	if (con == null)
		{
			return "Error while connecting to the database for updating.";
		}
		// create a prepared statement
		String query = "delete from complaints where complaintID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(complaintID));
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
	
	//read complaints related to a relevent account
	public String readsinglecomplaints(String complaintIDs)
	{
	String output = "";
	try
	{
	Connection con = dbconn.connect(); 
				if (con == null)
				{
					return "Error while connecting to the database for reading."; 
				}
					// Prepare the html table to be displayed
					output = "<table border='1'><tr><th>complaint type</th><th>contact number</th>" +
					"<th>message</th>" +
					"<th>Date</th>" +
					"<th>Reply</th>" +
					"<th>Status</th>" +
					"<th>Update</th><th>Remove</th></tr>";
					
					String query = "select * from complaints where AccNo=  " +complaintIDs;
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					// iterate through the rows in the result set
					while (rs.next())
					{
						String complaintID = Integer.toString(rs.getInt("complaintID"));
						String accno = rs.getString("AccNo");
						String complainttype = rs.getString("complaintType");
						String mobile = rs.getString("contactNo");
						String cmessage =  rs.getString("message");
						String datentime = rs.getString("date");
						String reply = rs.getString("replyMessage");
						String status = rs.getString("status");
						
						
						// Add into the html table
						output += "<tr><td>" + complainttype + "</td>";
						output += "<td>" + mobile + "</td>";
						output += "<td>" + cmessage + "</td>";
						output += "<td>" + datentime + "</td>";
						output += "<td>" + reply + "</td>";
						output += "<td>" + status + "</td>";
						// buttons
						output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + complaintID
						+ "'>" + "</form></td></tr>";
					}
					con.close();
					// Complete the html table
					output += "</table>";
				}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
			return output;
		}
	
}

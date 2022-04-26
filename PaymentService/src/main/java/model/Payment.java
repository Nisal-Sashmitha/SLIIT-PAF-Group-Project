package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Payment {
	
		//connection with the mysql workbench
		public Connection connect(){
			
			Connection con = null;
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electronicgriddb",
				"root", "1234");
				
				//For testing
				System.out.print("Successfully connected");
			}
			catch(Exception e)
			{
			e.printStackTrace();
			}
			return con;
		}
		
		//retrive payment details
		public String getPaymentDetails() {
			
			//variables
			String output=null;
			
			Connection con = connect();
			
			if(con==null) {
				output="DB connection error";
			}
			
			try {
						// Prepare the html table to be displayed
						output = "<table border='1'>"
						+ "<tr><th> paymentID</th><th>date</th><th>paidAmount</th>"
						+ "<th>AccNo</th><th>cardNo</th><th>email</th></tr>";
							
						//select query execution
						String query = "select * from payments";
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(query);
							
						// iterate through the rows in the result set
						while (rs.next())
						{
							int paymentID = rs.getInt("paymentID");
							String date = rs.getString("date");
							float paidAmount = rs.getFloat("paidAmount");
							String AccNo = rs.getString("AccNo");
							int cardNo = rs.getInt("cardNo");
							String email = rs.getString("email");
							
								
						// Add a row into the html table
							output += "<tr><td>" + paymentID + "</td>";
							output += "<td>" + date + "</td>";
							output += "<td>" + paidAmount + "</td>";
							output += "<td>" + AccNo + "</td>";
							output += "<td>" + cardNo + "</td>";
							output += "<td>" + email + "</td>";
							
						}
							
						con.close();
							
						output += "</table>";
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return output;
		}
		
		//insert payment
		public String insertPayment(float amount,String AccNo, int cardNo, String email) {
			
			//variables
			String output=null;
			String AccNo1=null;
			float creditBalance=0;
			
			//getting current date and format it
			LocalDate date = LocalDate.now();
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    String formattedDate = date.format(dateFormat);
			
			Connection con = connect();
			
			if(con==null) {
				output="connection error";
			}
			
			try {
				
				//validate the Account number 
				String query = "select * from account where AccNo="+AccNo;
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				
				while(rs.next()) {
					AccNo1=rs.getString("AccNo");
				}
				
				if(AccNo1==null) {
					output="invalid Account Number";
					return output;
				}
				
				//insert query execution
				String query1 = "insert into payments(`date`,`paidAmount`,`AccNo`,`cardNo`,`email`) values(?,?,?,?,?)";
				PreparedStatement statement = con.prepareStatement(query1);
				
				//bind the values to placeholder
				statement.setString(1,formattedDate);
				statement.setFloat(2, amount);
				statement.setString(3, AccNo);
				statement.setInt(4, cardNo);
				statement.setString(5, email);
				
				//execute the query
				statement.execute();
				
				//now let us update the creditBalance of the account
				//to do that first retrieve the current credit balance from account
				
				//query for retrieving the creditBalance from account table
				String query2 = "select creditBalance from account c where c.AccNo=" +AccNo;
				Statement stmt1 = con.createStatement();
				ResultSet res = stmt1.executeQuery(query2);
				
				if(res.next()) {
					creditBalance =  res.getFloat("creditBalance");
				}
				
				//new crdit balance
				creditBalance=creditBalance-amount;
				
				//calling the method to update account table
				updateAccount(AccNo,creditBalance);
				
				con.close();
				output="payment is successful";
				
			}
			catch(Exception e) {
				e.printStackTrace();			
				
			}
			return output;
		}
		
		//update account table
		public void updateAccount(String AccNo, float creditBalance) {
			
			System.out.println("inside update account");
			try {
				Connection con = connect();
				
				if(con==null) {
					System.out.println("connection error");
				}
				
				//update query
				String query="Update account set creditBalance=? where AccNo=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				//binding values into placeholder
				preparedStmt.setFloat(1, creditBalance);
				preparedStmt.setString(2,AccNo);
				
				//execute the query
				preparedStmt.execute();
				con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
		
		
}

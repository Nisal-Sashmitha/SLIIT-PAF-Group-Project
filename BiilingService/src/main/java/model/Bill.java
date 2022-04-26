package model;
import java.sql.*;

public class Bill {
	
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
	
	//retrive bill details
	public String getBillDetails() {
		
		//variables
		String output=null;
		
		Connection con = connect();
		
		if(con==null) {
			output="DB connection error";
		}
		
		try {
					// Prepare the html table to be displayed
					output = "<table border='1'>"
					+ "<tr><th>Account No</th><th>credit balance</th><th>MeterReadCurrentMonth</th>"
					+ "<th>MeterReadingLastMonth</th><th>status</th><th>year</th><th>month</th><th>monthlyCharge</th</tr>";
						
					//select query execution
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
						float monthlyCharge = rs.getFloat("monthlyCharge");
							
					// Add a row into the html table
						output += "<tr><td>" + AccNo + "</td>";
						output += "<td>" + creditBalance + "</td>";
						output += "<td>" + lastMeterReadCurrentMonth + "</td>";
						output += "<td>" + lastMeterReadingLastMonth + "</td>";
						output += "<td>" + status + "</td>";
						output += "<td>" + year + "</td>";
						output += "<td>" + month + "</td>";
						output += "<td>" + monthlyCharge + "</td>";
							
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
	
	public String getSingleBillDetails(String AccNo, int year, int month) {
		
		//variables
		String output=null;
		String AccNo1=null;
		
		try {
		Connection con = connect();
		
		if(con==null) {
			output="connection error";
		}
		
		//checking whether the account number is invalid 
		String query2 = "select AccNo from account c where c.AccNo=" +AccNo;
		Statement stmt1 = con.createStatement();
		ResultSet res1 = stmt1.executeQuery(query2);
		
		//binding billID, status values into variables 
		if(res1.next()) {
			AccNo1 =  res1.getString("AccNo");
		}
		
		if(AccNo1==null) {
			output="invalid Account number";
			return output;
		}
		
		// Prepare the html table to be displayed
		output = "<table border='1'>"
		+ "<tr><th>Account No</th><th>credit balance</th><th>MeterReadCurrentMonth</th>"
		+ "<th>MeterReadingLastMonth</th><th>status</th><th>year</th><th>month</th><th>monthlyCharge</th</tr>";
			
		//select query
		String query="select * from bill b where b.AccNo="+AccNo+ " and b.year="+year+ " and b.month="+month;
		Statement stmt = con.createStatement();
		
		//execute the query
		ResultSet rs = stmt.executeQuery(query);
		
		// iterate through the rows in the result set
		while (rs.next())
		{
			int billID = rs.getInt("billID");
			String accNo = Integer.toString(rs.getInt("AccNo"));
			float creditBalance = rs.getFloat("creditBalance");
			int lastMeterReadCurrentMonth = rs.getInt("lastMeterReadingsCurrentMonth");
			int lastMeterReadingLastMonth = rs.getInt("lastMeterReadingsPreviousMonth");
			String status = rs.getString("status");
			int year1 = rs.getInt("year");
			int month1 = rs.getInt("month");
			float monthlyCharge = rs.getFloat("monthlyCharge");
				
		// Add a row into the html table
			output += "<tr><td>" + accNo + "</td>";
			output += "<td>" + creditBalance + "</td>";
			output += "<td>" + lastMeterReadCurrentMonth + "</td>";
			output += "<td>" + lastMeterReadingLastMonth + "</td>";
			output += "<td>" + status + "</td>";
			output += "<td>" + year1 + "</td>";
			output += "<td>" + month1 + "</td>";
			output += "<td>" + monthlyCharge + "</td>";
			
		}
		
		con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	//insert method
	public String InsertBill(String AccNo,int lastMeterReadCurrentMonth,int lastMeterReadingLastMonth,int year, int month) {
		
		//variables
		String result=null;
		String status="stable";
		String AccNo1=null;
		float creditBalance=0;
		Connection con = connect();
		
		if(con==null) {
			result="DB connection error";
		}
		
		//calculate the monthly charge
		float monthlyCharge=calMonthluCharge(lastMeterReadingLastMonth,lastMeterReadCurrentMonth);
		
		//query for retrieving the creditBalance from account table
		String query1 = "select creditBalance,AccNo from account c where c.AccNo=" +AccNo;
		
		//insert query to insert bill record
		String query= "insert into Bill(`AccNo`,`creditBalance`,`lastMeterReadingsPreviousMonth`,`lastMeterReadingsCurrentMonth`,`status`,`year`,`month`,`monthlyCharge`)" + "values(?,?,?,?,?,?,?,?)";
		
		try {
			
			//Execute the query1
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(query1);
			
			//filtering creditBalance and AccNo 
			if(res.next()) {
				creditBalance =  res.getFloat("creditBalance");
				AccNo1=res.getString("AccNo");		
			}
			
			//check whether the account number is valid
			if(AccNo1==null) {
				result="invalid account number";
				return result;
			}
			
			//new credit balance
			creditBalance+=monthlyCharge;
			
			//changing the status depending on the creditBalance
			if(creditBalance>=3000) {
				status="warning";
			}
			
			//updating the creditBalance in account table
			updateAccount(AccNo,creditBalance);
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//here the parameters values of insert method assigned to placeholder in prepared statement
			System.out.println("accont no" + AccNo);
			preparedStmt.setString(1, AccNo);
			preparedStmt.setFloat(2, creditBalance);
			preparedStmt.setInt(3,lastMeterReadCurrentMonth);
			preparedStmt.setInt(4,lastMeterReadingLastMonth);
			preparedStmt.setString(5, status);
			preparedStmt.setInt(6, year);
			preparedStmt.setInt(7, month);
			preparedStmt.setFloat(8, monthlyCharge);
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			result="Succesfully inserted";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//calculate the monthly charge
	public float calMonthluCharge(int currentreading, int previousReading) {
		int difference = currentreading - previousReading;
		float total=0;
		
		if(difference<=60) {
			if(difference<=30) {
				total+=difference*2.50+30;
			}
			else {
				int range= difference-30;
				total+=30*2.25 + 30 + range*4.85+60;
			}
		}
		else {
			int range = difference-60;
			total+= 60*7.85;
			
			if(range<=30) {
				total+=10*range+90;
			}
			else if(range<=60) {
				total+=30*10+90;
				int range2=range-30;
				total+=27.75*range2+480;
			}
			else if (range<=120) {
				total+=30*10+90;
				total+=27.75*30+480;
				int range3=range-60;
				total+=32*range3+480;
			}
			else if(range>120) {
				total+=30*10+90;
				total+=27.75*30+480;
				total+=32*60+480;
				int range4=range-120;
				total+=45*range4+540;
			}
			
		}
		
		return total;
	}
	
	//bill update method
	public String updateBillDetails(String ExistingAccNo,int newLastMeterReadingsPreviousMonth, int newLastMeterReadingsCurrentMonth,int ExistingYear,int ExistingMonth) {
		
		//variables
		String output=null;
		String status=null;
		int newBillID=0;
		String AccNo1=null;
		float creditBalance=0;
		float prevMonthlyCharge=0;
		float newMonthlyCharge=calMonthluCharge(newLastMeterReadingsCurrentMonth,newLastMeterReadingsPreviousMonth);
		
		try {
			
			Connection con = connect();
			
			if(con==null) {
				output= "error while connecting to the database";
			}
			
			//checking whether the account number is invalid 
			String query2 = "select AccNo from account c where c.AccNo=" +ExistingAccNo;
			Statement stmt1 = con.createStatement();
			ResultSet res1 = stmt1.executeQuery(query2);
			
			//binding billID, status values into variables 
			if(res1.next()) {
				AccNo1 =  res1.getString("AccNo");
			}
			
			if(AccNo1==null) {
				output="invalid Account number";
				return output;
			}
			
			//trying to get the billID, status,monthlyCharge and creditBalance of the particular record
			String query1="select b.billID,b.status, b.monthlyCharge, b.creditBalance from bill b where b.AccNo =" +ExistingAccNo+" and b.year = " +ExistingYear+ " and b.month =" + ExistingMonth;
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(query1);
			
			//binding billID, status values into variables 
			if(res.next()) {
				newBillID =  res.getInt("billID");
				status = res.getNString("status");
				prevMonthlyCharge=res.getFloat("monthlyCharge");
				creditBalance=res.getFloat("creditBalance");
			}
			
			//since the previous meter reading is not valid, we need to subtract the previous month's charge from the creditBalance
			creditBalance = creditBalance - prevMonthlyCharge;
			
			//calculate new credit balance by adding new monthlyCharge into it
			creditBalance = creditBalance + newMonthlyCharge;
			
			//let us update the creditBalnce in Account table
			updateAccount(ExistingAccNo,creditBalance);
			
			//query to update the bill record 
			String query="Update bill set AccNo=?,creditBalance=?,lastMeterReadingsPreviousMonth=?,"
					+ "lastMeterReadingsCurrentMonth=?,status=?, year=?,month=?,monthlyCharge=? where BillID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values into columns 
			//preparedStmt.setInt(1, newBillID);
			preparedStmt.setString(1, ExistingAccNo);
			preparedStmt.setFloat(2, creditBalance);
			preparedStmt.setInt(3,newLastMeterReadingsPreviousMonth);
			preparedStmt.setInt(4,newLastMeterReadingsCurrentMonth);
			preparedStmt.setString(5,status);
			preparedStmt.setInt(6,ExistingYear);
			preparedStmt.setInt(7, ExistingMonth);
			preparedStmt.setFloat(8, newMonthlyCharge);
			preparedStmt.setInt(9, newBillID);
			
			//execute the query
			preparedStmt.execute();
			con.close();
			
			output="successfully updated";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	//update account
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
	
	
	
	//Delete method
	public String deleteBill(String AccNo, int year, int month) {
		
		//variables
		String output=null;
		String AccNo1=null;
		try {
			Connection con = connect();
			
			if(con==null) {
				output="Connection error";
			}
			
			//checking whether the account number is invalid 
			String query1 = "select AccNo from account c where c.AccNo=" +AccNo;
			Statement stmt1 = con.createStatement();
			ResultSet res1 = stmt1.executeQuery(query1);
			
			//binding billID, status values into variables 
			if(res1.next()) {
				AccNo1 =  res1.getString("AccNo");
			}
			
			if(AccNo1==null) {
				output="invalid Account number";
				return output;
			}
			
			//delete query
			String query="delete from bill where AccNo=? and year=? and month=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values into columns 
			preparedStmt.setString(1, AccNo);
			preparedStmt.setInt(2,year);
			preparedStmt.setInt(3,month);
			
			//execute the query
			preparedStmt.execute();
			con.close();
			output="successfully deleted";
		}
		catch(Exception e) {
			e.printStackTrace();		
		}
		
		return output;
	}
	
}

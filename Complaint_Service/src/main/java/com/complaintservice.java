package com;

import javax.ws.rs.Consumes;


import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.complaint;

//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Complaints")
public class complaintservice {
	
	
	complaint Complaintobj = new complaint();
	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
		return Complaintobj.readItems();
	
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("AccNo") String Acc_no,
	@FormParam("complaintType") String complaint_T,		
	@FormParam("contactNo") String contact_no,
	@FormParam("message") String message)
	{
	String output =  Complaintobj.Createcomplaint(Acc_no,complaint_T, contact_no, message);
	return output;
	}
	
	
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateComplaint(String itemData)
	{
	//Convert the input string to a JSON object
	JsonObject Complaintobject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	String complainid = Complaintobject.get("complaintID").getAsString();
	String Accountno = Complaintobject.get("AccNo").getAsString();
	String complainttype = Complaintobject.get("complaintType").getAsString();
	String mobile = Complaintobject.get("contactNo").getAsString();
	String c_message = Complaintobject.get("message").getAsString();
	String c_date = Complaintobject.get("date").getAsString();
	String A_reply = Complaintobject.get("replyMessage").getAsString();
	String A_status = Complaintobject.get("status").getAsString();
	
	String output = Complaintobj.updateComplaint(complainid, Accountno, complainttype, mobile,c_message,c_date,A_reply,A_status);
	return output;
	}

	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String complaintData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(complaintData, "", Parser.xmlParser());
	//Read the value from the element <itemID>
	String complaintID = doc.select("complaintID").text();
	String output =  Complaintobj.deleteItem(complaintID);
	return output;
	}
	


}

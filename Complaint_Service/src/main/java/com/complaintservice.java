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
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Complaints")
public class complaintservice {
	
	
	//create a object of the complaint model class
	complaint Complaintobj = new complaint();
	
	
	//Read  all complaints
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readComplaints()
	{
		return Complaintobj.readcomplaints();
	
	}

	//read pending complaints 
	@GET
	@Path("/Pending")
	@Produces({ MediaType.TEXT_HTML })
	public String readsComplaints() {
		
		return Complaintobj.readpendingcomplaints();
	}
	
	//read complaints related to a single account
	@GET
	@Path("/Sacoount")
	@Produces({ MediaType.TEXT_HTML })
	@Consumes(MediaType.APPLICATION_JSON)
	public String readComplaints(String id) {
		JsonObject complaintObject = new JsonParser().parse(id).getAsJsonObject();
		String complaintID = complaintObject.get("AccNo").getAsString();
		return Complaintobj.readsinglecomplaints(complaintID );
	}
	
	
	//insert data 
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertcomplaint(String complaintdata)
	{
		
		//Convert the input string to a JSON object
		 JsonObject complaintobject = new JsonParser().parse(complaintdata).getAsJsonObject();
		//Read the values from the JSON object
		 
		 String token = complaintobject.get("token").getAsString();
			
		//check validity of the request
		 if(!this.verifyUser(token)) {
				 
			 return "Couldn't Insert. Unauthorised User!";
		 }
		 
		 String Acc_no = complaintobject.get("AccNo").getAsString();
		 String complaint_T = complaintobject.get("complaintType").getAsString();
		 String contact_no = complaintobject.get("contactNo").getAsString();
		 String message = complaintobject.get("message").getAsString();
		 
		 
		
		
	String output =  Complaintobj.Createcomplaint(Acc_no,complaint_T, contact_no, message);
	return output;
	}
	
	
	
	//Update data
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateComplaint(String complaintData)
	{
		//Convert the input string to a JSON object
		JsonObject Complaintobject = new JsonParser().parse(complaintData).getAsJsonObject();
		//Read the values from the JSON object
		String token = Complaintobject.get("token").getAsString();
		
		//check validity of the request
		 if(!this.verifyUser(token)) {
			 
			 return "Couldn't update. Unauthorised User!";
		 }
		//Read the values from the JSON object
		String complainid = Complaintobject.get("complaintID").getAsString();
	
		String A_reply = Complaintobject.get("replyMessage").getAsString();
		String A_status = Complaintobject.get("status").getAsString();
		
		String output = Complaintobj.updateComplaint(complainid,A_reply,A_status);
		return output;
	}

	//delete complaint
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteComplaint(String complaintData)
	{
		//Convert the input string to a JSON object
		JsonObject complaintObject = new JsonParser().parse(complaintData).getAsJsonObject();
		
		String token = complaintObject.get("token").getAsString();
		
		//check validity of the request
		 if(!this.verifyUser(token)) {
			 
			 return "Couldn't delete. Unauthorised User!";
		 }
		//Read the value from the JSON object
		String ComplaintID = complaintObject.get("complaintID").getAsString();
		String output = Complaintobj.deletecomplaint(ComplaintID);
		return output;
	}
	
	
	//Verify consumer for inter-service communication
	public boolean verifyUser(String token)
	{
		System.out.println("here");
		ClientResponse response;
		boolean output ;
		try {

	        Client client = Client.create();
	        
	        WebResource webResource = client
	          .resource("http://localhost:8084/ConsumerService/ConsumerService/Auth/verify");
	        System.out.println("here2");
	        response = webResource.accept("text/plain")
	          .post(ClientResponse.class,token);

	        output = Boolean.parseBoolean(response.getEntity(String.class))  ;
	        System.out.println(response.getStatus());
	        System.out.println("!"+output+"!");
	        
	      } catch (Exception e) {
	    	  return false;
	      }
		return output;
		
	}
	


}

package com;

import model.Interruptions;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Path("/Interruptions")
public class InterruptionCalenderService {
	
	
	Interruptions interruptionObj = new Interruptions();
	
	
	
	
	/*--------------------insert interruptions --------------------*/
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertInterruptions(String interruptionData)
	{
		try {
			//Convert the input string to a JSON object
			 JsonObject innerInterruptionObj = new JsonParser().parse(interruptionData).getAsJsonObject();
			//Read the values from the JSON object
			 
			 String token = innerInterruptionObj.get("token").getAsString();
				
			//check validity of the request
			 if(!this.verifyUser(token)) {
					 
				 return "Couldn't Insert. Unauthorised User!";
			 }
			 
			 
			
			 String date = innerInterruptionObj.get("date").getAsString();
			 String startTime = innerInterruptionObj.get("startTime").getAsString();
			 String endTime = innerInterruptionObj.get("endTime").getAsString();
			 String areaID = innerInterruptionObj.get("areaID").getAsString();
			 String output = interruptionObj.newInterruption(date, startTime, endTime, areaID);
			 return output;
			
		}catch(Exception e) {
			return "invalid input format";
		}
		
	}
	
	
	/*--------------------update interruption--------------------*/
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateInterruption(String interruptionData)
	{
		try {
		//Convert the input string to a JSON object
		JsonObject interruptionDataObject = new JsonParser().parse(interruptionData).getAsJsonObject();
    	//Read the values from the JSON object
		String token = interruptionDataObject.get("token").getAsString();
		
		//check validity of the request
		 if(!this.verifyUser(token)) {
				 
			 return "Couldn't Update. Unauthorised User!";
		 }
		
		String interruptionID = interruptionDataObject.get("interruptionID").getAsString();
		String date = interruptionDataObject.get("date").getAsString();
		String startTime = interruptionDataObject.get("startTime").getAsString();
		String endTime = interruptionDataObject.get("endTime").getAsString();
		String areaID = interruptionDataObject.get("areaID").getAsString();
		String output = interruptionObj.editInterruptions(interruptionID, date, startTime, endTime, areaID);
		return output;
		
		}catch(Exception e) {
			return "invalid input format!";
		}
		
	}
	
	
	
	
	/*--------------------delete interruption--------------------*/
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String interruptionData)
	{
		try {
			//Convert the input string to a JSON object
			JsonObject interruptionDataObject = new JsonParser().parse(interruptionData).getAsJsonObject();
			//Read the value from the json object
			String token = interruptionDataObject.get("token").getAsString();
		
			//check validity of the request
			if(!this.verifyUser(token)) {
			 
				return "Couldn't delete. Unauthorised User!";
			}
		 
		 
		
		
			String InterruptionID = interruptionDataObject.get("interruptionID").getAsString();
			String output = interruptionObj.deleteInterruption(InterruptionID);
			return output;
		
		}catch(Exception e) {
			return "invalid input format!";
		}
	}
	
	//view interruption
		//view interruptions in a area for a day
		
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String readItems(String interruptionAreaData)
	 {
		try {
			//Convert the input string to a JSON object
			JsonObject interruptionDataObject = new JsonParser().parse(interruptionAreaData).getAsJsonObject();
			
			//Read the value from the json object
			String areaID = interruptionDataObject.get("areaID").getAsString();
			String date = interruptionDataObject.get("date").getAsString();
			return interruptionObj.InterruptionsOfTheArea (areaID, date);
		
	 	}catch(Exception e) {
			return "invalid input format!";
		}
	}
	
		//view interruptions for an account
	@GET
	@Path("/InterruptionNotification")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getNotifications(String interruptionAreaData)
	 {
		try {
			//Convert the input string to a JSON object
			JsonObject interruptionDataObject = new JsonParser().parse(interruptionAreaData).getAsJsonObject();
		
			//Read the value from the json object
			String accountNo = interruptionDataObject.get("accountNo").getAsString();
			return interruptionObj.viewNotifications ( accountNo);
	 	}catch(Exception e) {
			return "invalid input format!";
		}
	}
	
	
	public boolean verifyUser(String token)
	{
		System.out.println("here");
		ClientResponse response;
		boolean output ;
		try {

	        Client client = Client.create();
	        
	        WebResource webResource = client
	          .resource("http://localhost:8086/ConsumerService/ConsumerService/Auth/verify");
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

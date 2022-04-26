package com;

import javax.swing.text.Document;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.Parser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.*;
import model.Bill;


@Path("/Bill")
public class BillService
{
	Bill billObject =new Bill();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String billDetails()
	{
		return billObject.getBillDetails();
	}
	
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String getSingleRecord(String data)
	{
		//convert the input string into a JSON object
		JsonObject itemObject = new JsonParser().parse(data).getAsJsonObject();
		
		//Read the value from the json object
		String token = itemObject.get("token").getAsString();
		
		//check validity of the request
		 if(!this.verifyUser(token)) {
			 
			 return "Couldn't perform the task. Unauthorised User!";
		 }
		
		//Read the values from the JSON object
		String AccNo = itemObject.get("AccNo").getAsString();
		int year = itemObject.get("year").getAsInt();
		int month = itemObject.get("month").getAsInt();
		
		String output = billObject.getSingleBillDetails(AccNo, year, month);
		return output;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("AccNo") String AccNo,
			@FormParam("lastMeterReadCurrentMonth") int lastMeterReadCurrentMonth,
			@FormParam("lastMeterReadingLastMonth") int lastMeterReadingLastMonth,
			@FormParam("year") int year,
			@FormParam("month") int month,
			@FormParam("token") String token) {
		
		//check validity of the request
		 if(!this.verifyUser(token)) {
			 
			 return "Couldn't insert. Unauthorised User!";
		 }
		
		System.out.println("account no in service" + AccNo);
		return billObject.InsertBill(AccNo,lastMeterReadCurrentMonth ,lastMeterReadingLastMonth, year, month);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBillRecord(String itemData)
	{
		//convert the input string into a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		
		//Read the value from the json object
				String token = itemObject.get("token").getAsString();
				
				//check validity of the request
				 if(!this.verifyUser(token)) {
					 
					 return "Couldn't update. Unauthorised User!";
				 }
		
		//Read the values from the JSON object
		String ExistingAccNo = itemObject.get("ExistingAccNo").getAsString();
		int newLastMeterReadingsPreviousMonth = itemObject.get("newPreviousMonthReading").getAsInt();
		int newLastMeterReadingsCurrentMonth = itemObject.get("newCurrentMonthReading").getAsInt();
		int ExistingYear = itemObject.get("ExistingYear").getAsInt();
		int ExistingMonth = itemObject.get("ExistingMonth").getAsInt();

		
		String output = billObject.updateBillDetails(ExistingAccNo, newLastMeterReadingsPreviousMonth,
				newLastMeterReadingsCurrentMonth,ExistingYear,ExistingMonth);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBill(@FormParam("AccNo") String AccNo, 
			@FormParam("year") int year, 
			@FormParam("month") int month,
			@FormParam("token") String token){
		
		//check validity of the request
		 if(!this.verifyUser(token)) {
			 
			 return "Couldn't delete. Unauthorised User!";
		 }
		String output= billObject.deleteBill(AccNo, year, month);
		return output;
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

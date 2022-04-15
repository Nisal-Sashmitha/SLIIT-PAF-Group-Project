package com;

import model.Interruptions;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Path("/Interruptions")
public class InterruptionCalenderService {
	
	
	Interruptions interruptionObj = new Interruptions();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readInterruptions()
	 {
		return "Hello";
	 }
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(String itemData)
	{
		//Convert the input string to a JSON object
		 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		//Read the values from the JSON object
		
		 String date = itemObject.get("date").getAsString();
		 String startTime = itemObject.get("startTime").getAsString();
		 String endTime = itemObject.get("endTime").getAsString();
		 String areaID = itemObject.get("areaID").getAsString();
		 String output = interruptionObj.newInterruption(date, startTime, endTime, areaID);
		 return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateInterruption(String interruptionData)
	{
		//Convert the input string to a JSON object
		JsonObject interruptionDataObject = new JsonParser().parse(interruptionData).getAsJsonObject();
    	//Read the values from the JSON object
		String InterruptionID = interruptionDataObject.get("InterruptionID").getAsString();
		String date = interruptionDataObject.get("date").getAsString();
		String startTime = interruptionDataObject.get("startTime").getAsString();
		String endTime = interruptionDataObject.get("endTime").getAsString();
		String areaID = interruptionDataObject.get("areaID").getAsString();
		String output = interruptionObj.editInterruptions(InterruptionID, date, startTime, endTime, areaID);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String interruptionData)
	{
		//Convert the input string to a JSON object
		JsonObject interruptionDataObject = new JsonParser().parse(interruptionData).getAsJsonObject();
		//Read the value from the element <itemID>
		String InterruptionID = interruptionDataObject.get("interruptionID").getAsString();
		String output = interruptionObj.deleteInterruption(InterruptionID);
		return output;
	}

	

}

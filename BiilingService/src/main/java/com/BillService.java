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

import javax.ws.rs.*;
import model.Bill;


@Path("/BillService")
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
			@FormParam("month") int month) {
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
	public String deleteBill(@FormParam("AccNo") String AccNo, @FormParam("year") int year, @FormParam("month") int month)
	{
		
		String output= billObject.deleteBill(AccNo, year, month);
		return output;
	}
}

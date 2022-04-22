package com;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
	
	@POST
	@Path("/insertBill")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("AccNo") String AccNo,
			@FormParam("creditBalance") float creditBalance,
			@FormParam("lastMeterReadCurrentMonth") int lastMeterReadCurrentMonth,
			@FormParam("lastMeterReadingLastMonth") int lastMeterReadingLastMonth,
			@FormParam("status") String status,
			@FormParam("year") int year,
			@FormParam("month") int month) {
		return billObject.InsertBill(AccNo, creditBalance,lastMeterReadCurrentMonth ,lastMeterReadingLastMonth,status, year, month);
	}
}

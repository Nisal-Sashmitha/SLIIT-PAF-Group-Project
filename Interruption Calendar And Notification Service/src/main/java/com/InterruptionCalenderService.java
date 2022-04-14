package com;

import model.Interruptions;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/Interruptions")
public class InterruptionCalenderService {
	
	
	Interruptions itemObj = new Interruptions();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readInterruptions()
	 {
		return "Hello";
	 }
	

}

package com;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/BillService")
public class BillService
{
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String billDetails()
	{
		return "Hello world.";
	}
}

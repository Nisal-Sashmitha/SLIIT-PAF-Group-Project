package com;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;


import javax.ws.rs.core.Response;






@Path("/Auth")
public class authService
{
	@POST
	@Path("/verify")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response hello(String val) throws URISyntaxException 
	{
		System.out.println("In auth:"+val);
		if(Integer.parseInt(val)%2==0) {
			return Response.status(Response.Status.OK).entity("true").build();
		}else {
			return Response.status(Response.Status.OK).entity("false").build();
		}
		

		
	}
} 
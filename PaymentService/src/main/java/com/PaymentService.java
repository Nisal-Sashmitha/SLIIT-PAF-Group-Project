package com;

import java.util.Date;
import java.sql.*;

import javax.swing.text.Document;
import javax.websocket.server.PathParam;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.Parser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.*;
import model.Payment;

@Path("/Payments")
public class PaymentService {
	
	Payment obj = new Payment();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String PaymentDetails()
	{
		return obj.getPaymentDetails();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(
			@FormParam("paidAmount") float paidAmount,
			@FormParam("AccNo") String AccNo,
			@FormParam("cardNo") int cardNo,
			@FormParam("email") String email,
			@FormParam("token") String token) {
		
		//check validity of the request
		 if(!this.verifyUser(token)) {
			 
			 return "Couldn't insert. Unauthorised User!";
		 }
		 
		String output= obj.insertPayment(paidAmount,AccNo, cardNo, email);
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

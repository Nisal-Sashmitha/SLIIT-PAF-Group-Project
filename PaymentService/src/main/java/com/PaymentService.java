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

import javax.ws.rs.*;
import model.Payment;

@Path("/Payments")
public class PaymentService {
	
	Payment obj = new Payment();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String billDetails()
	{
		return obj.getPaymentDetails();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(
			@FormParam("paidAmount") float paidAmount,
			@FormParam("AccNo") String AccNo,
			@FormParam("cardNo") int cardNo,
			@FormParam("email") String email) {
		String output= obj.insertPayment(paidAmount,AccNo, cardNo, email);
		return output;
	}
	
}

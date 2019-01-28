package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


@Path("/main")
public class GuessController {
	public static Random rand = new Random();
	public static int numberOfBytes =  rand.nextInt(3) + 1;
	public static byte[] byteArray = new byte[numberOfBytes];
	public static boolean guessed = true;
	public static byte[] digest = null;
	public static String inputToGuess = null;
	public static String hashToGuess = null;
	
	
	@GET
	@Path("/hash")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException, NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		if (guessed){
			numberOfBytes = rand.nextInt(3) + 1;
			byteArray = new byte[numberOfBytes];
			rand.nextBytes(byteArray);
			digest = md.digest(byteArray);
			guessed = false;
		}
		hashToGuess = Base64.getEncoder().encodeToString(digest);
		inputToGuess = Base64.getEncoder().encodeToString(byteArray);
		System.out.println("Hash To Guess: "  + hashToGuess);
		System.out.println("Input To Guess: " + inputToGuess);
		return Response.created(new URI("/main")).entity(new GuessSerializable(hashToGuess,numberOfBytes)).build();
	}

	@POST
	@Path("/hash")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(String json){
		ObjectMapper om = new ObjectMapper();
		try {
			JsonSerializable jsonInput = om.readValue(json, JsonSerializable.class);
			String hash = jsonInput.HASH;
			String input = jsonInput.INPUT;

			
			if(!hash.equals(hashToGuess)) {
				System.out.println("Wrong hash");
				return Response.status(406).build();
			}
			if(!input.equals(inputToGuess)) {
				System.out.println("Wrong input");
				return Response.status(406).build();
			}
			guessed = true;
			System.out.println("They Guessed It");
			return Response.status(200).build();
		} catch (Exception e) {
			System.out.println("Wrong input");
			return Response.status(406).build();
		}
		
	}
}

package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import org.eclipse.jdt.internal.compiler.ast.SynchronizedStatement;


@Path("/game")
public class GameController {
	
	public static HashMap<String,String> games = new HashMap<String, String>();
	public static HashMap<String,Integer> turnsCounter = new HashMap<String, Integer>();
	public static HashMap<String,Boolean> successChecker = new HashMap<String,Boolean>();
	
	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException{
		Random r = new Random();
		String numbers = "";
		int number;
		while(numbers.length() != 4) {
			if(!numbers.contains(String.valueOf(number = r.nextInt(10)))){
				numbers+=String.valueOf(number);
			}
		}

		String gameId = UUID.randomUUID().toString();

		System.out.println(numbers);
		games.put(gameId, numbers);
		turnsCounter.put(gameId,0);
		successChecker.put(gameId,false);
		return Response.created(new URI("/games")).entity(gameId).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception{
		int bulls = 0;
		int cows = 0;
		boolean success = false;
		
		if(!games.containsKey(gameId)) {
			return Response.status(404).build();
		}
		
		if((guess.length() != 4) || !guess.matches("[12345678910]\\d{3}")) {
			System.out.println("hi1");
			return Response.status(404).build();
		}
		
		if(guess.equals(games.get(gameId))) {
			success = true;
			bulls = 4;
			successChecker.put(gameId, true);
		}else {
			turnsCounter.put(gameId, turnsCounter.get(gameId) + 1);
			
			for(int i = 0; i < 4; i++) {
				if(guess.charAt(i) == games.get(gameId).charAt(i)) {
					cows --;
					bulls ++;
				}
			}
			
			for(int i = 0; i < 4; i++) {
				if(games.get(gameId).indexOf(guess.charAt(i)) >= 0) {
					cows++;
				}
			}
		
		}
		
		return Response.status(200).entity(new GuessSerializable(gameId,cows,bulls,turnsCounter.get(gameId),success)).build();
		
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() {
		if(games.size()== 0 ) {
			return Response.status(404).build();
		}else {
			List<GamesSerializable> allGames = new ArrayList<GamesSerializable>();
			for(String gameId : games.keySet()) {
				String secret = null;
				if(successChecker.get(gameId) != true) {
					secret = "****";
				}else {
					secret = games.get(gameId);
				}
				allGames.add(new GamesSerializable(gameId,turnsCounter.get(gameId),secret,successChecker.get(gameId)));
			}
			
			return Response.status(200).entity(allGames).build();
		}
		
	}
}

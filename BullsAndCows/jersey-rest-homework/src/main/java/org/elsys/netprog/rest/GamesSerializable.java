package org.elsys.netprog.rest;

public class GamesSerializable {
	public String gameId;
	public int turnsCount;
	public String secret;
	public boolean success;
	
	public GamesSerializable(String gameId, int turnsCount, String secret, boolean success) {
		this.gameId = gameId;
		this.turnsCount = turnsCount;
		this.secret = secret;
		this.success = success;
	}
}

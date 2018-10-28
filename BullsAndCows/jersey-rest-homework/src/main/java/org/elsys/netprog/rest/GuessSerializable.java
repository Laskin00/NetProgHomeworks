package org.elsys.netprog.rest;

public class GuessSerializable {
	public String gameId;
	public int cows;
	public int bulls;
	public int turnsCount;
	public boolean success;
	
	public GuessSerializable(String gameId, int cows, int bulls, int turnsCount, boolean success) {
		this.gameId = gameId;
		this.cows = cows;
		this.bulls = bulls;
		this.turnsCount = turnsCount;
		this.success = success;
	}
}

package org.elsys.netprog.rest;

public class GuessSerializable {
	public String gameId;
	public int cowsNumber;
	public int bullsNumber;
	public int turnsCount;
	public boolean success;
	
	public GuessSerializable(String gameId, int cows, int bulls, int turnsCount, boolean success) {
		this.gameId = gameId;
		this.cowsNumber = cows;
		this.bullsNumber = bulls;
		this.turnsCount = turnsCount;
		this.success = success;
	}
}

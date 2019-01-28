package org.elsys.netprog.rest;

public class GuessSerializable {
	public String digest;
	public int length;
	
	public GuessSerializable(String digest, int length) {
		this.digest = digest;
		this.length = length;
	}
}

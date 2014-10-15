package com.liero.exceptions;

public class WorldNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6383603170439053150L;
	
	public WorldNotFoundException() {
		super("Cannot find world");
	}
}

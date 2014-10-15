package com.liero.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7518796026097573106L;

	public ObjectAlreadyExistsException(String message) {
		super(message);
	}
}

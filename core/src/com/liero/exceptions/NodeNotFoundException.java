package com.liero.exceptions;

public class NodeNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9194725057928423602L;
	
	public NodeNotFoundException(int x, int y) {
		super("Cannot find node at:" + x + ", " + y);
	}
}

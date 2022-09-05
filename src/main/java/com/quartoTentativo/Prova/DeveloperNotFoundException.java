package com.quartoTentativo.Prova;

public class DeveloperNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DeveloperNotFoundException(Long id) {
	    super("Could not find developer " + id);
	  }

}

package com.quartoTentativo.Prova;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class DeveloperNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DeveloperNotFoundException(Long id) {
	    super("Could not find developer " + id + System.lineSeparator() 
	+ "Query " + linkTo(methodOn(DevelopersController.class).all()) + " for a list of all registered developers");
	  }

}

package com.quartoTentativo.Prova;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TaskNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TaskNotFoundException(long id){
		super("Could not find task " + id + System.lineSeparator() +
				 "Query " + linkTo(methodOn(TasksController.class).all()) + " for a list of all registered tasks");
	}

}

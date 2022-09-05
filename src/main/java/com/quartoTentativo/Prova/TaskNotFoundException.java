package com.quartoTentativo.Prova;

public class TaskNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TaskNotFoundException(long id){
		super("Could not find task" + id);
	}

}

package com.quartoTentativo.Prova;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {
	
	@Override
	public EntityModel<Task> toModel(Task task){
		
		EntityModel<Task> taskModel;
		
		taskModel = EntityModel.of(task, 
				linkTo(methodOn(TasksController.class).one(task.getId())).withSelfRel(),
		        linkTo(methodOn(TasksController.class).all()).withRel("tasks"),
		        linkTo(methodOn(TasksController.class).addNote("", task.getId())).withRel("addNote"));
		
		if (task.getCondition() == STATE.inBacklog) 
			taskModel.add(linkTo(methodOn(TasksController.class).commenceWork(task.getId())).withRel("beginWork"));
		
		if (task.getCondition() == STATE.inProgress) {
			taskModel.add(linkTo(methodOn(TasksController.class).complete(task.getId())).withRel("endWork"),
					linkTo(methodOn(TasksController.class).clockIn(0f, task.getId())).withRel("addHours"));
			//There is a 0f that I don't THINK is an issue since to make the right link all you need is the id
		}
		
		if (task.getCondition() == STATE.completed) 
			taskModel.add(linkTo(methodOn(TasksController.class).commenceWork(task.getId())).withRel("restartWork"));
		
		
		return taskModel;
	}
}

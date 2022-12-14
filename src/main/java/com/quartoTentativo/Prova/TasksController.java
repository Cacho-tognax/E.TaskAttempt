package com.quartoTentativo.Prova;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



//TODO: HTML response codes; Handling of invalId requests

@RestController
class TasksController {

	private final TaskRepository repository;
	private final TaskModelAssembler assembler;

	TasksController(TaskRepository repository, TaskModelAssembler assembler){
		this.repository = repository;
		this.assembler = assembler;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/tasks")
	CollectionModel<EntityModel<Task>> all() {
		List<EntityModel<Task>> tasks = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(tasks, linkTo(methodOn(TasksController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/tasks")
	EntityModel<Task>newTask(@RequestBody Task newTask) {
		return assembler.toModel(repository.save(newTask));
	}

	// Single item

	@GetMapping("/tasks/{taskId}")
	EntityModel<Task> one(@PathVariable Long taskId) {
		Task task = repository.findById(taskId)
				.orElseThrow(() -> new TaskNotFoundException(taskId));

		return assembler.toModel(task);
	}

	@PutMapping("/tasks/{taskId}")
	EntityModel<Task> replaceTask(@RequestBody Task newTask, @PathVariable Long taskId) {

		return assembler.toModel(repository.findById(taskId)
				.map(task -> {
					task.setName(newTask.getName());
					task.setHoursWorked(newTask.getHoursWorked());
					return repository.save(task);
				})
				.orElseGet(() -> {
					newTask.setId(taskId);
					return repository.save(newTask);
				}));
	}

	@DeleteMapping("/tasks/{taskId}")
	void deleteTask(@PathVariable Long taskId) {
		repository.deleteById(taskId);
	}

	@PutMapping("/tasks/{taskId}/begin")
	EntityModel<Task> commenceWork(@PathVariable Long taskId) {
		return assembler.toModel(repository.findById(taskId).map(task -> {
			task.setCondition(STATE.inProgress);
			return repository.save(task);
			})
				.orElseThrow(() -> new TaskNotFoundException(taskId)));
		
	}
	
	@PutMapping("/tasks/{taskId}/complete")
	EntityModel<Task> complete(@PathVariable Long taskId) {
		return assembler.toModel(repository.findById(taskId).map(task -> {
			task.setCondition(STATE.completed);
			return repository.save(task);
			})
				.orElseThrow(() -> new TaskNotFoundException(taskId)));
		
	}
	
	@PutMapping("/tasks/{taskId}/work")
	EntityModel<Task> clockIn(@RequestBody String hours, @PathVariable Long taskId) {
		Double hour = Double.valueOf(hours);
		return assembler.toModel(repository.findById(taskId).map(task -> {
			task.addHrs(hour);
			return repository.save(task);
		})
				.orElseThrow(() -> new TaskNotFoundException(taskId)));
	}
	
	@PutMapping("/tasks/{taskId}/addNote")
	EntityModel<Task> addNote(@RequestBody String note, @PathVariable Long taskId) {
		return assembler.toModel(repository.findById(taskId).map(task -> {
			task.addNote(note);
			return repository.save(task);
		})
				.orElseThrow(() -> new TaskNotFoundException(taskId)));
		
	}
}

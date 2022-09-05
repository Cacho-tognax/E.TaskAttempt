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



//TODO: HTML response codes; Handling of invalid requests

@RestController
class DevelopersController {

	private final DeveloperRepository repository;
	private final DeveloperModelAssembler assembler;

	DevelopersController(DeveloperRepository repository, DeveloperModelAssembler assembler){
		this.repository = repository;
		this.assembler = assembler;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/developers")
	CollectionModel<EntityModel<Developer>> all() {

		List<EntityModel<Developer>> developers = repository.findAll().stream()
				.map(assembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(developers, linkTo(methodOn(DevelopersController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/developers")
	Developer newDeveloper(@RequestBody Developer newDeveloper) {
		return repository.save(newDeveloper);
	}

	// Single item

	@GetMapping("/developers/{developerId}")
	EntityModel<Developer> one(@PathVariable Long developerId) {

		Developer developer = repository.findById(developerId)
				.orElseThrow(() -> new DeveloperNotFoundException(developerId));
		return assembler.toModel(developer);
	}

	@PutMapping("/developers/{developerId}")
	EntityModel<Developer> replaceDeveloper(@RequestBody Developer newDeveloper, @PathVariable Long developerId) {

		return assembler.toModel(repository.findById(developerId)
				.map(developer -> {
					developer.setFirstName(newDeveloper.getFirstName());
					developer.setLastName(newDeveloper.getLastName());
					return repository.save(developer);
				})
				.orElseGet(() -> {
					newDeveloper.setId(developerId);
					return repository.save(newDeveloper);
				}));
	}

	@DeleteMapping("/developers/{developerId}")
	void deleteDeveloper(@PathVariable Long developerId) {
		repository.deleteById(developerId);
	}
	
	@PutMapping("/developers/{developerId}/assign")
	EntityModel<Developer> assignTask(@RequestBody Long taskId, @PathVariable Long developerId){
		Developer developer = repository.findById(developerId)
				.orElseThrow(() -> new DeveloperNotFoundException(developerId));
		developer.assignTask(taskId);
		return assembler.toModel(developer);
	}
	
	@PutMapping("/developers/{developerId}/work")
	EntityModel<Developer> workHours(@RequestBody float hours, @PathVariable Long developerId){
		Developer developer = repository.findById(developerId)
				.orElseThrow(() -> new DeveloperNotFoundException(developerId));
		developer.addHrs(hours);
		return assembler.toModel(developer);
	}
}

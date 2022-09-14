package com.quartoTentativo.Prova;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DeveloperModelAssembler implements RepresentationModelAssembler<Developer, EntityModel<Developer>> {
	
	@Override
	public EntityModel<Developer> toModel(Developer developer){
		
		return EntityModel.of(developer, 
		linkTo(methodOn(DevelopersController.class).one(developer.getId())).withSelfRel(),
        linkTo(methodOn(DevelopersController.class).all()).withRel("developers"),
        linkTo(methodOn(DevelopersController.class).assignTask("", developer.getId())).withRel("assignTask"),
        linkTo(methodOn(DevelopersController.class).workHours("", developer.getId())).withRel("addHours"));
	}
}

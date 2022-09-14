package com.quartoTentativo.Prova;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(DeveloperRepository repository) {

		return args -> {
			repository.save(new Developer("Default", "Tizio", 0d));
			repository.save(new Developer("Default", "Sempronio", 0d));
		};
	}

	@Bean
	CommandLineRunner initDatabase2(TaskRepository repository) {
		return args -> {
			repository.save(new Task("inizio", 0d));
			repository.save(new Task("intermezzo", 0d));
		};
	}
}
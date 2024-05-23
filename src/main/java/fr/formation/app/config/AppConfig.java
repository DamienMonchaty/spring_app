package fr.formation.app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.formation.app.models.Person;

@Configuration // permet de déclarer un composant configurant le contexte de l application
public class AppConfig {
	
	// @Service, @Repository, @Component
	@Bean
	Person person() {
		return new Person(1, "NOM1", "PRENOM", 40);
	}
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}

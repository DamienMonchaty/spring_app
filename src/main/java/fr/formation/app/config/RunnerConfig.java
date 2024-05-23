package fr.formation.app.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fr.formation.app.dao.RoleRepository;
import fr.formation.app.dto.PersonDTO;
import fr.formation.app.models.Role;
import fr.formation.app.models.RoleName;
import fr.formation.app.services.IPersonService;

@Component
public class RunnerConfig implements CommandLineRunner {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Demarrage de l application !");
		personService.saveOrUpdate(new PersonDTO("PRENOM1", "NOM1", 40));
		personService.saveOrUpdate(new PersonDTO("PRENOM2", "NOM2", 40));
		
		roleRepository.saveAll(Arrays.asList(
				new Role(RoleName.ROLE_USER), 
				new Role(RoleName.ROLE_ADMIN)
				)
			);
	}

}
